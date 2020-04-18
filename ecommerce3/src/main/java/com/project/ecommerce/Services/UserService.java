package com.project.ecommerce.Services;

import com.project.ecommerce.Entities.User;
import com.project.ecommerce.Entities.User1;
import com.project.ecommerce.Exceptions.ConfirmPasswordException;
import com.project.ecommerce.Exceptions.UserAlreadyRegisteredException;
import com.project.ecommerce.Repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    EmailService emailService;

    public List<User1> getAllUsers() {

        List<User> list = userRepository.getAllUsers();
        List<User1> list1=new ArrayList<User1>();
        for (User user: list) {
            User1 user1=new User1();
            user1.setUserid(user.getUserid());
            user1.setFirstname(user.getFirstname());
            user1.setMiddlename(user.getMiddlename());
            user1.setLastname(user.getLastname());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setIs_active(user.isIs_active());
            user1.setIs_deleted(user.isIs_deleted());

            list1.add(user1);
        }
        return list1;
    }

    public void addUser(User user) {
        System.out.println(user.getPassword());
        String password=user.getPassword();
        if (userRepository.findByUsername(user.getEmail())==null){
            String confirmPassword=user.getConfirmPassword();
            if(password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                System.out.println(user.getPassword());
                userRepository.save(user);
            }
            else
                throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }
        else
                throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");
}

    @Transactional
    public String activateUser(String email) {
        User user= userRepository.findByUsername(email);
        userRepository.activateUser(user.getUserid());
        emailService.sendEmail("SUCCESSFULLY Registered", "Hii \n Your account have been activated," +
                " now you can logged in using http://localhost:8080/oauth/token",user.getEmail());
        return "User account has been activated";
    }

    public String deActivateUser(String email) {
        User user= userRepository.findByUsername(email);
        userRepository.deActivateUser(user.getUserid());
        emailService.sendEmail("MALICIOUS ACTION FOUND", "Hii \n We have found some malicious action performed through account" +
                " As a result, your account has been temporarily de-activated.",user.getEmail());
        return "User account has been de-activated";
    }
}
