package com.project.ecommerce.service;

import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.entity.User;
import com.project.ecommerce.entity.VerificationToken;
import com.project.ecommerce.exception.ConfirmPasswordException;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.exception.ValidationException;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;


    public String forgotPassword(String email, Locale locale) {
        User user=userRepository.findByUsername(email);
        if(user.getEmail()==null)
            throw new UserNotFoundException("User doesn't exist");
        if(!user.isIs_active())
            throw new UserNotFoundException("User is not active");
        else{
            String token= UUID.randomUUID().toString();
            VerificationToken verificationToken=new VerificationToken();
            verificationToken.setUserEmail(user.getEmail());
            verificationToken.setGeneratedDate(new Date());
            verificationToken.setToken(token);
            emailService.sendEmail("RESET YOUR PASSWORD","Hii, \nUse this link to reset your password ->" +
                    " http://localhost:8080/password/reset/"+token,email);
            verificationTokenRepository.save(verificationToken);
        }
        return messageSource.getMessage("forgot.password.message", null, locale);
    }

    public String resetPassword(PasswordDto passwordDto, String token,Locale locale) {
        VerificationToken verificationToken = verificationTokenRepository.getByToken(token);
        String password = passwordDto.getPassword();
        String confirmPassword = passwordDto.getConfirmPassword();
        if (verificationToken.getUserEmail() == null)
            return "http://localhost:8080/password/reset/" + token + "is Invalid";
        else {
            if (!password.equals(confirmPassword)) {
                throw new ConfirmPasswordException("Password and confirm password not match");
            }
            else {
                String email = verificationToken.getUserEmail();
                User user = userRepository.findByUsername(email);
                    try {
                        emailService.sendEmail("ACCOUNT SECURITY ISSUE", "Hii, \nYour password has been changed.", user.getEmail());
                        String encodedPassword = passwordEncoder.encode(password);
                        userRepository.updatePassword(encodedPassword, user.getEmail());
                        verificationTokenRepository.deleteById(verificationToken.getTokenId());
                    }
                    catch (Exception ex){
                        throw new ValidationException("Hit the reset password link again.");
                    }
            }
            return messageSource.getMessage("reset.password.message", null, locale);
        }
    }
}