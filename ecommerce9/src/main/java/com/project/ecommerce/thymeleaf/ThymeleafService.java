package com.project.ecommerce.thymeleaf;

import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThymeleafService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ModelMapper modelMapper;



    public List<CustomerThymeleafDto> getAllCustomers() {

        List<CustomerThymeleafDto> customerThymeleafDtoList = new ArrayList<>();

        if (customerRepository.count() > 0) {

            customerRepository
                    .findAll()
                    .forEach(p ->
                    { CustomerThymeleafDto customerThymeleafDto =
                            modelMapper.map(p, CustomerThymeleafDto.class);

                    customerThymeleafDtoList.add(customerThymeleafDto);
            });
        }

        else {
            throw new UserNotFoundException("User Not Found");
        }
        return customerThymeleafDtoList;
    }



    public List<SellerThymeleafDto> getAllSellers() {

        List<SellerThymeleafDto> sellerThymeleafDtoList = new ArrayList<>();

        if (sellerRepository.count() > 0) {

            sellerRepository.findAll().forEach(p -> {
                SellerThymeleafDto sellerThymeleafDto = modelMapper.map(p, SellerThymeleafDto.class);
                sellerThymeleafDtoList.add(sellerThymeleafDto);
            });
        }

        else {
            throw new UserNotFoundException("User Not Found");
        }
        return sellerThymeleafDtoList;
    }
}
