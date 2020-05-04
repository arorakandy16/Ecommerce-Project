package com.project.ecommerce.repository;


import com.project.ecommerce.entity.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Long> {
}
