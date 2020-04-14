package com.project.ecommerce.service;

import com.project.ecommerce.dao.PasswordDto;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.entities.VerificationToken;
import com.project.ecommerce.exception.ConfirmPasswordException;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.repositries.UserRepository;
import com.project.ecommerce.repositries.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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


    public String forgotPassword(String email) {
        System.out.println(email+">>>>>>>>>");
        User user=userRepository.findByUsername(email);
        System.out.println(user.getEmail());
        if(user.getEmail()==null)
            throw new UserNotFoundException("User doesn't exist");
        else{
            String token= UUID.randomUUID().toString();
            VerificationToken verificationToken=new VerificationToken();
            verificationToken.setUserEmail(user.getEmail());
            verificationToken.setGeneratedDate(new Date());
            verificationToken.setToken(token);
            emailService.sendEmail("RESET YOUR PASSWORD","Hii, \nUse this link to reset your password ->" +
                    " http://localhost:8080/reset-password/"+token,email);
            verificationTokenRepository.save(verificationToken);
        }
        return "Kindly check your email to reset Password.";
    }

    public String resetPassword(PasswordDto passwordDto, String token) {
        VerificationToken verificationToken = verificationTokenRepository.getByToken(token);
        String password = passwordDto.getPassword();
        String confirmPassword = passwordDto.getConfirmpassword();
        if (verificationToken.getUserEmail() == null)
            return "http://localhost:8080/reset-password/" + token + "has been expired";
        else {
            if (!password.equals(confirmPassword)) {
                throw new ConfirmPasswordException("Password and confirm password not match");
            }
            else {
                String email = verificationToken.getUserEmail();
                User user = userRepository.findByUsername(email);
                if (user.getEmail() == null)
                    throw new UserNotFoundException("user not exist");
                else {
                    try {
                        emailService.sendEmail("ACCOUNT SECURITY ISSUE", "Hii, \nYour password has been changed.", user.getEmail());
                        String encodedPassword = passwordEncoder.encode(password);
                        userRepository.updatePassword(encodedPassword, user.getEmail());
                        verificationTokenRepository.deleteById(verificationToken.getTokenId());
                    }
                    catch (Exception ex){
                        return "Hit the reset password link again.";
                    }
                }
            }
            return "password has changed successfully";
        }
    }
}