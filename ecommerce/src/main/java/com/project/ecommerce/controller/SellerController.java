package com.project.ecommerce.controller;

import com.project.ecommerce.entities.Seller;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.service.ProductService;
import com.project.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;
    
    @Autowired
    ProductService productService;

    @PostMapping("/sellerRegistration")
    public String addSeller(@RequestBody Seller seller) {
        String message = sellerService.addseller(seller);
        return message;
    }

    @GetMapping("/getSellers")
    public List<Seller> getAllSellers() {
        System.out.println("*************All Sellers ********");
         return sellerService.listAllSeller();
         
    }
//    //Firstname
//    @PutMapping(value="/update-seller-fname/{id}")
//    public User updateFirstname(@PathVariable Integer id,@RequestBody User firstname){
//        sellerService.editSellerFname(id,firstname);
//
//        return sellerService.findSellerbyId(id);
//    }
//    //Lastname
//    @PutMapping(value="/update-seller-lname/{id}")
//    public User updateLastname(@PathVariable Integer id,@RequestBody User lastname){
//        sellerService.editSellerLname(id,lastname);
//
//        return sellerService.findSellerbyId(id);
//    }
//
//    //MiddleName
//    @PutMapping(value="/update-seller-Middlename/{id}")
//    public User updateMiddlename(@PathVariable Integer id,@RequestBody User middlename){
//        sellerService.editSellerMname(id,middlename);
//
//        return sellerService.findSellerbyId(id);
//    }
//
//    //Email
//    @PutMapping(value="/update-seller-email/{id}")
//    public User updateEmail(@PathVariable Integer id,@RequestBody User email){
//        sellerService.editSellerEmail(id,email);
//
//        return sellerService.findSellerbyId(id);
//    }
//
//    //Password
//    @PutMapping(value="/update-seller-password/{id}")
//    public User updatePassword(@PathVariable Integer id,@RequestBody User password){
//        sellerService.editSellerPassword(id,password);
//
//        return sellerService.findSellerbyId(id);
//    }
//    //GST
//    @PutMapping(value="/update-seller-gst/{id}")
//    public User updateGST(@PathVariable Integer id,@RequestBody Seller GST){
//        sellerService.editSellerGST(id,GST);
//
//        return sellerService.findSellerbyId(id);
//    }
//
//    //CompanyContact
//    @PutMapping(value="/update-seller-contact/{id}")
//    public User updateCompanyContact(@PathVariable Integer id,@RequestBody Seller companyContact){
//        sellerService.editSellerCompanyContact(id,companyContact);
//
//        return sellerService.findSellerbyId(id);
//    }
//    //CompanyName
//    @PutMapping(value="/update-seller-companyName/{id}")
//    public User updateCompanyName(@PathVariable Integer id,@RequestBody Seller companyName){
//        sellerService.editSellerCompanyName(id,companyName);
//
//        return sellerService.findSellerbyId(id);
//    }

    @PutMapping(value="/update-seller/{id}")
    public String editSeller(@PathVariable Integer id,@RequestBody Seller seller){
        System.out.println(">>"+seller.getEmail());
        sellerService.editSeller(id,seller);

        return "Seller updated...";
    }

    //****** Mapping Seller with product *************
    
//    adding product---
//    @PostMapping("/seller/addProduct")
//    public String addProduct(@RequestBody Product product) {
//        sellerService.addseller(seller);
//        return "product added successfully.";
//    }

//    @GetMapping(path = "/all-products-of-seller/{id}")
//    public void productBySellerId(@PathVariable(value = "id")Integer id)
//    {
//        productService.allProductsOfSeller(id);
//    } 
//    
    
    
    
    
    
    
    
}