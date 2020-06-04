package com.project.ecommerce.thymeleaf;

import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.entity.Seller;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class Thymeleaf {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;



    @GetMapping("/home")
    public String home() {
        return "home";
    }



    @RequestMapping(value = "/list/Customers", method = RequestMethod.GET)
    public String listCustomer(Model model){

        List<Customer> customerList = (List<Customer>) customerRepository.findAll();

        model.addAttribute("customerList",customerList);

        return "customer";
    }


    @RequestMapping(value = "/list/Sellers", method = RequestMethod.GET)
    public String listSellers(Model model){

        List<Seller> sellerList = (List<Seller>) sellerRepository.findAll();

        model.addAttribute("sellerList",sellerList);

        return "seller";
    }
}
