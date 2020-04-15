package com.project.ecommerce.services;

import com.project.ecommerce.repositries.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

//    @Transactional
//    public List<> listAllSeller(){
//        return sellerRepository.findAllSeller(PageRequest.of(0,10, Sort.Direction.ASC,"user_id"));
//    }

//    public List<Object[]>  allProductsOfSeller(Long id)
//    {
//        return productRepository.findSellerProducts(id);
//    }
}
