package com.project.ecommerce.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThymeleafController {

    @Autowired
    ThymeleafService thymeleafService;

    @GetMapping("/listCustomers")
    public List<CustomerThymeleafDto> listCustomers() {
        return thymeleafService.getAllCustomers();
    }

    @GetMapping("/listSellers")
    public List<SellerThymeleafDto> listSellers() {
        return thymeleafService.getAllSellers();
    }
}