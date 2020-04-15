package com.project.ecommerce.controllers;

import com.project.ecommerce.dtos.PasswordDto;
import com.project.ecommerce.dtos.SellerDto;
import com.project.ecommerce.dtos.SellerProfileDto;
import com.project.ecommerce.entities.Seller;
import com.project.ecommerce.services.AddressService;
import com.project.ecommerce.services.ProductService;
import com.project.ecommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;
    
    @Autowired
    ProductService productService;

    @Autowired
    AddressService addressService;

    @PostMapping("/seller/registration")
    public String addSeller(@RequestBody Seller seller) {
        String message = sellerService.addseller(seller);
        return message;
    }

    @GetMapping("/getSellers")
    public List<Seller> getAllSellers() {
        return sellerService.listAllSeller();
    }

    @PutMapping("/seller/update/profile")
    public String editSeller(@RequestBody SellerDto seller){
//        System.out.println(">>"+seller.getEmail());
        sellerService.editSeller(seller);
        return "Seller updated...";
    }

    @PutMapping("/seller/update/password")
    public String updatePassword(@RequestBody PasswordDto passwordDto){
        sellerService.updatePassword(passwordDto);
        return "Customer updated...";
    }

    @GetMapping("/seller/myProfile")
    public SellerProfileDto myProfile() {
        return sellerService.myProfile();
    }

}