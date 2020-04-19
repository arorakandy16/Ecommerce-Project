package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CustomerDto;
import com.project.ecommerce.dto.CustomerProfileDto;
import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/customer/registration")
    public String addCustomer
            (@Valid @RequestBody Customer user,@RequestHeader
                    (name = "Accept-Language", required = false) Locale locale) {
        String message = customerService.addCustomer(user,locale);
        return message;
    }

    @GetMapping("/customer/get/all")
    public List<Customer> getAllCustomers() {
        return customerService.listAllCustomers();

    }

    @GetMapping("/customer/activate-account/{token}")
    public String activateCustomer
            (@PathVariable String token,@RequestHeader
                    (name = "Accept-Language", required = false) Locale locale){
        String message = customerService.activateCustomer(token,locale);
        return message;
    }

    @GetMapping("/re-sent-link/{email}")
    public String reSentLink
            (@PathVariable String email,@RequestHeader
                    (name = "Accept-Language", required = false) Locale locale) {
        String message = customerService.reSentLink(email,locale);
        return message;
    }

    @PutMapping("/customer/update/profile")
    public String editCustomer
            (@RequestBody CustomerDto customer,@RequestHeader
                    (name = "Accept-Language", required = false) Locale locale){
        customerService.editCustomer(customer);
        return messageSource.getMessage("customer.update.message", null, locale);
    }

    @PutMapping("customer/update/password")
    public String updatePassword
            (@RequestBody PasswordDto passwordDto,@RequestHeader
                    (name = "Accept-Language", required = false) Locale locale){
        customerService.updateCustomerPassword(passwordDto);
        return messageSource.getMessage("customer.update.password.message", null, locale);
    }

    @GetMapping("/customer/myProfile")
    public CustomerProfileDto myProfile(){
        return customerService.myProfile();
    }
}
