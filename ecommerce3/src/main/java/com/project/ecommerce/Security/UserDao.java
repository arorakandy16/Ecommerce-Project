package com.project.ecommerce.Security;

import com.project.ecommerce.Entities.User;
import com.project.ecommerce.Exceptions.UserNotFoundException;
import com.project.ecommerce.Repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    UserRepository userRepository;
    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (username != null) {
            return new AppUser(user.getEmail(), user.getPassword(), user.getRoles(),user.isIs_active());
        } else {
            throw new UserNotFoundException("User not Found...");
        }

    }
}
