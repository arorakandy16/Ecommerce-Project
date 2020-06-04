package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.entity.Admin;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private MessageSource messageSource;



    @GetMapping("/user/get/all")
    public List<UserDto> getAllUsers
            (@RequestHeader(name = "offset",
                    required = false,
                    defaultValue = "0") Integer offset,
             @RequestHeader(name = "size",
                     required = false,
                     defaultValue = "10") Integer size){
        return userService.getAllUsers(offset, size);
    }



    @PostMapping("/admin/register")
    public String addAdmin(@RequestBody Admin user,
                           @RequestHeader(name = "Accept-Language",
                                   required = false) Locale locale) {
        userService.addUser(user,locale);
        return messageSource.getMessage("admin.add.message", null, locale);
    }



    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request,
                         @RequestHeader(name = "Accept-Language",
                                 required = false) Locale locale){

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return messageSource.getMessage("logout.message", null, locale);
    }



    @PutMapping("/user/activate-account/{id}")
    public String activateUser(@PathVariable Long id,
                               @RequestHeader(name = "Accept-Language",
                                       required = false) Locale locale){
        return userService.activateUser(id,locale);
    }



    @PutMapping("/user/de-activate-account/{id}")
    public String deActivateUser(@PathVariable Long id,
                                 @RequestHeader(name = "Accept-Language",
                                         required = false) Locale locale){
        return userService.deActivateUser(id,locale);
    }

}