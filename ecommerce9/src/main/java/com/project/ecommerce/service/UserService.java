package com.project.ecommerce.service;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.entity.Admin;
import com.project.ecommerce.entity.User;
import com.project.ecommerce.entity.VerificationToken;
import com.project.ecommerce.exception.*;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;


    @Autowired
    private MessageSource messageSource;




    // Get All Users

    public List<UserDto> getAllUsers(Integer offset, Integer size) {

        List<User> list = userRepository.getAllUsers
                (PageRequest.of
                        (offset, size, Sort.Direction.ASC,"userid"));

        List<UserDto> list1=new ArrayList<UserDto>();

        for (User user: list) {
            UserDto user1=new UserDto();
            user1.setUserId(user.getUserid());
            user1.setFirstName(user.getFirstname());
            user1.setMiddleName(user.getMiddlename());
            user1.setLastName(user.getLastname());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setIs_active(user.isIs_active());
            user1.setIs_deleted(user.isIs_deleted());
            user1.setUserImage(user.getUserImage());

            list1.add(user1);
        }
        return list1;
    }




    //Add User

    public void addUser(Admin user,Locale locale) {
        System.out.println(user.getPassword());

        String password = user.getPassword();

        if (userRepository.findByUsername(user.getEmail()) == null) {
            String confirmPassword = user.getConfirmPassword();

            if (password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                user.setIs_active(true);
                emailService.sendEmail
                        ("ADMIN REGISTERED","Hii, " +
                                        "\n Your email "+user.getEmail()
                                +"  is registered as Admin",
                        user.getEmail());
                userRepository.save(user);

                throw new Message(messageSource.getMessage
                        ("admin.add.message", null, locale));
            }

            else

                throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }

        else

            throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");

    }



    //Activate User

    @Transactional
    public String activateUser(Long id, Locale locale) {
        Optional<User> user= userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("Invalid User Id");
        if(user.get().isIs_active())
            throw new UserNotFoundException("User is already active");
        userRepository.activateUser(id);
        emailService.sendEmail("SUCCESSFULLY Registered", "Hii \n Your account have been activated," +
                " now you can logged in using http://localhost:8080/oauth/token",user.get().getEmail());
        throw new Message(messageSource.getMessage("user.activate.message", null, locale));
    }




    //De-activate a User

    @Transactional
    public String deActivateUser(Long id,Locale locale) {
        Optional<User> user= userRepository.findById(id);

        if(!user.isPresent())
            throw new UserNotFoundException("Invalid User Id");

        if(!user.get().isIs_active())
            throw new UserNotFoundException("User is not active");

        userRepository.deActivateUser(id);

        emailService.sendEmail("ACCOUNT DEACTIVATED : MALICIOUS ACTION FOUND",
                "Hii \n We have found some malicious action performed through account" +
                " As a result, your account has been temporarily de-activated."
                ,user.get().getEmail());
        throw new Message(messageSource.getMessage
                ("user.deactivate.message", null, locale));
    }


    //-------------------------------Scheduling-------------------------------------------------

    //Scheduling is a process of executing the tasks for the specific time period.
    //Java Cron expressions are used to configure the instances of CronTrigger
    @Scheduled(cron = "0 00 00 * * ?")

    public void scheduling(){

        System.out.println("This is a Scheduler");
    }

}
