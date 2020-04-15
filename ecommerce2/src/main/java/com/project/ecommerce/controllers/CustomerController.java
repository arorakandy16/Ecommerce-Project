package com.project.ecommerce.controllers;

import com.project.ecommerce.dtos.CustomerDto;
import com.project.ecommerce.dtos.CustomerProfileDto;
import com.project.ecommerce.dtos.PasswordDto;
import com.project.ecommerce.entities.Customer;
import com.project.ecommerce.repositries.UserRepository;
import com.project.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/customer/registration")
    public String addCustomer(@Valid @RequestBody Customer user) {
        String message = customerService.addCustomer(user);
        return message;
    }

    @GetMapping("/getCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.listAllCustomers();

    }

    @GetMapping("/activate-customer-account/{token}")
    public String activateCustomer(@PathVariable String token){
        String message = customerService.activateCustomer(token);
        return message;
    }

    @GetMapping("/re-sent-link/{email}")
    public String reSentLink(@PathVariable String email) {
        String message = customerService.reSentLink(email);
        return message;
    }

    @PutMapping("/customer/update/Profile")
    public String editCustomer(@RequestBody CustomerDto customer){
        customerService.editCustomer(customer);
        return "Customer updated...";
    }

    @PutMapping("customer/update/password")
    public String updatePassword(@RequestBody PasswordDto passwordDto){
        customerService.updateCustomerPassword(passwordDto);
        return "Password updated...";
    }

    @GetMapping("/customer/myProfile")
    public CustomerProfileDto myProfile(){
        return customerService.myProfile();
    }
}
