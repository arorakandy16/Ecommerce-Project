package com.project.ecommerce.controller;
import com.project.ecommerce.dao.PasswordDto;
import com.project.ecommerce.entities.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.ecommerce.service.ForgotPasswordService;

import javax.validation.Valid;

@RestController
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping(path="/forgot-password")
    public String forgotPassword(@RequestBody User1 user){
        return forgotPasswordService.forgotPassword(user.getEmail());
    }

    @PostMapping(path="reset-password/{token}")
    public String resetPassword(@Valid @RequestBody PasswordDto passwordDto, @PathVariable String token){
        return forgotPasswordService.resetPassword(passwordDto,token);
    }
}