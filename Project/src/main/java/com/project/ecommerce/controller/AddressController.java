package com.project.ecommerce.controller;

import com.project.ecommerce.entity.Address;
import com.project.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Set;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/customer/address/get/all")
    public Set<Address> viewCustomerAddresses(){
        return  addressService.viewCustomerAddress();
    }

    @PostMapping("/customer/address/add")
    public String addCustomerAddress(@RequestBody Address address,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        addressService.addCustomerAddress(address);
        return messageSource.getMessage("customer.add.address.message", null, locale);
    }

    @PutMapping("/customer/address/update/{AddressId}")
    public String updateCustomerAddress(@Valid @RequestBody Address address, @PathVariable Long AddressId,@RequestHeader(name = "Accept-Language", required = false)Locale locale){
        addressService.updateCustomerAddress(address,AddressId);
        return messageSource.getMessage("customer.update.address.message", null, locale);
    }

    @DeleteMapping("/customer/address/delete/{addressId}")
    public String deleteCustomerAddress(@Valid @PathVariable Long addressId,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        addressService.deleteCustomerAddress(addressId);
        return messageSource.getMessage("customer.delete.address.message", null, locale);
    }

    @PutMapping("/seller/address/update/{addId}")
    public String updateSellerAddress(@PathVariable Long addId, @RequestBody Address address,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        addressService.updateSellerAddress(address,addId);
        return messageSource.getMessage("seller.update.address.message", null, locale);
    }
}
