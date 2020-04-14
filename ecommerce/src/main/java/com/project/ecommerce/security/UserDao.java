package com.project.ecommerce.security;

import com.project.ecommerce.entities.User;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserDao {
    @Autowired
    UserRepository userRepository;
    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (username != null) {
            return new AppUser(user.getEmail(), user.getPassword(), user.getRoles(),user.isIs_active());
        } else {
            throw new UserNotFoundException("User not Found...");
        }

    }
}
