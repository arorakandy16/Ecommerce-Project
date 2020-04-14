//package com.project.ecommerce.dao;
//
//import com.project.ecommerce.entities.User;
//import com.project.ecommerce.exception.UserNotFoundException;
//import com.project.ecommerce.repositries.UserRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserDao {
//
//    @Autowired
//    UserRepository userRepository;
//
//    public AppUser loadUserByUsername(String email) {
//        User user = userRepository.findByEmail(email);
//        System.out.println(user);
//        if (email != null) {
//            return new AppUser(user);
////            return new AppUser(user.getEmail(), user.getPassword(), Arrays.asList(new GrantAuthorityImpl(user.getRole())));
//        } else {
//            throw new UserNotFoundException("user  " + user.getEmail() + " was not found");
//        }
//
//    }
//}
