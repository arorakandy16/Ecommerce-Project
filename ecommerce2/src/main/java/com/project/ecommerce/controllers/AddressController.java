package com.project.ecommerce.controllers;

import com.project.ecommerce.entities.Address;
import com.project.ecommerce.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/customer/getAddress")
    public Set<Address> viewCustomerAddresses(){
        return  addressService.viewCustomerAddress();
    }

    @PostMapping("/customer/addAddress")
    public String addCustomerAddress(@RequestBody Address address){
        addressService.addCustomerAddress(address);
        return "Customer Address is added successfully";
    }

    @PutMapping("/customer/updateAddress/{AddressId}")
    public void updateCustomerAddress(@Valid @RequestBody Address address, @PathVariable Long AddressId){
        addressService.updateCustomerAddress(address,AddressId);
    }

    @DeleteMapping("/customer/deleteAddress/{AddressId}")
    public String deleteCustomerAddress(@Valid @PathVariable Long AddressId){
        return addressService.deleteCustomerAddress(AddressId);
    }


    @PutMapping("/seller/updateAddress/{addId}")
    public String updateSellerAddress(@PathVariable Long addId, @RequestBody Address address){
        addressService.updateSellerAddress(address,addId);
        return "Address Updated.";
    }
}
