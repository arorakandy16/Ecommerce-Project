package com.project.ecommerce.Controllers;

import com.project.ecommerce.Daos.CustomerDto;
import com.project.ecommerce.Daos.CustomerProfileDto;
import com.project.ecommerce.Daos.PasswordDto;
import com.project.ecommerce.Entities.Customer;
import com.project.ecommerce.Repositries.UserRepository;
import com.project.ecommerce.Services.CustomerService;
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

    @PutMapping("/customer/update/profile")
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
