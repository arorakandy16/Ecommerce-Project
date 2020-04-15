package com.project.ecommerce.controllers;

import com.project.ecommerce.entities.User;
import com.project.ecommerce.entities.User1;
import com.project.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/getUsers")
    public List<User1> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/Admin/Register")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User added successfully.";
    }

    
    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @GetMapping("/activate-user/{email}")
    public String activateUser(@PathVariable String email){
        String message = userService.activateUser(email);
        return message;
    }

    @GetMapping("/de-activate-user/{email}")
    public String deActivateUser(@PathVariable String email){
        String message = userService.deActivateUser(email);
        return message;
    }
}