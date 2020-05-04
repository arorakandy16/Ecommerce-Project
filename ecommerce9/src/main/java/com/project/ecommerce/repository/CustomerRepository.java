package com.project.ecommerce.repository;


import com.project.ecommerce.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {

    @Query("from Customer")
    List<Customer> findAllCustomer(Pageable pageable);

    @Query("from Customer where email=:username")
    Customer findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "update Customer set firstname=:firstname ,middlename=:middleName ," +
            "lastname=:lastName ,contact=:contact where userid=:userid")
    void updateCustomer(@Param("userid") Long id, @Param("firstname") String firstname,
                      @Param("middleName") String middleName, @Param("lastName") String lastName,
                      @Param("contact") Long contact);

    @Transactional
    @Modifying
    @Query(value = "update Customer set is_active=:is_active where email=:email")
    void updateIsActive(@Param("is_active") boolean is_active,String email);
}
