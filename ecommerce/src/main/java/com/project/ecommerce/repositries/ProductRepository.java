package com.project.ecommerce.repositries;

import com.project.ecommerce.entities.Product;
import com.project.ecommerce.entities.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductCategory,Integer> {
    
}
