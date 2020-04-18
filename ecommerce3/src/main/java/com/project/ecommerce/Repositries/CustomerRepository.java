package com.project.ecommerce.Repositries;


import com.project.ecommerce.Entities.Customer;
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

//    @Query(value = "select user_id from customer where user_id=:user_id",nativeQuery = true)
//    Integer customerUserId(@Param("user_id") Long user_id);

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
