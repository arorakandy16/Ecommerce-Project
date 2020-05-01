package com.project.ecommerce.security;

import com.project.ecommerce.entity.User;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    UserRepository userRepository;
    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user.getEmail() != null) {
            return new AppUser(user.getEmail(), user.getPassword(), user.getRoles(),user.isIs_active());
        } else {
            throw new UserNotFoundException("User not Found...");
        }
    }
}
