package com.project.ecommerce.repositries;


import com.project.ecommerce.entities.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Integer> {
}
