package com.project.ecommerce.Repositries;


import com.project.ecommerce.Entities.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Long> {
}
