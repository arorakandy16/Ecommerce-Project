package com.project.ecommerce.controller;

import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import com.project.ecommerce.service.ForgotPasswordService;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class ForgotPasswordController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/password/forgot")
    public String forgotPassword(@RequestBody UserDto user,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return forgotPasswordService.forgotPassword(user.getEmail(),locale);
    }

    @PutMapping("/password/reset/{token}")
    public String resetPassword(@Valid @RequestBody PasswordDto passwordDto, @PathVariable String token,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return forgotPasswordService.resetPassword(passwordDto,token,locale);
    }
}