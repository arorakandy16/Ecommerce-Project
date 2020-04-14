package com.project.ecommerce.controller;

import com.project.ecommerce.entities.Customer;
import com.project.ecommerce.repositries.UserRepository;
import com.project.ecommerce.service.CustomerService;
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

    @PostMapping("/customerRegistration")
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

    @PutMapping(value="/update-customer/{id}")
    public String editSeller(@PathVariable Integer id,@RequestBody Customer customer){
        customerService.editCustomer(id,customer);
        return "Customer updated...";
    }
}
