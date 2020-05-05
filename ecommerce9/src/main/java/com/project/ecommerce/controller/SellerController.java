package com.project.ecommerce.controller;

import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.dto.SellerDto;
import com.project.ecommerce.dto.SellerProfileDto;
import com.project.ecommerce.entity.Seller;
import com.project.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    private MessageSource messageSource;



    @PostMapping("/seller/registration")
    public String addSeller(@RequestBody Seller seller,
                            @RequestHeader(name = "Accept-Language",
                                    required = false) Locale locale) {
        String message = sellerService.addSeller(seller,locale);
        return message;
    }



    @GetMapping("/seller/get/all/{page}/{size}")
    public List<Seller> getAllSellers(@PathVariable Integer page,
                                      @PathVariable Integer size) {
        return sellerService.listAllSeller(page, size);
    }



    @PutMapping("/seller/update/profile")
    public String editSeller(@RequestBody SellerDto seller,
                             @RequestHeader(name = "Accept-Language",
                                     required = false) Locale locale){
        sellerService.editSeller(seller,locale);
        return messageSource.getMessage("seller.update.message", null, locale);
    }



    @PutMapping("/seller/update/password")
    public String updatePassword(@RequestBody PasswordDto passwordDto,
                                 @RequestHeader(name = "Accept-Language",
                                         required = false) Locale locale){
        sellerService.updatePassword(passwordDto,locale);
        return messageSource.getMessage("seller.update.password.message", null, locale);
    }



    @GetMapping("/seller/myProfile")
    public SellerProfileDto myProfile() {
        return sellerService.myProfile();
    }
}