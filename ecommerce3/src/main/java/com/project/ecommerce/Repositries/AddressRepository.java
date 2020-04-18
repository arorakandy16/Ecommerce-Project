package com.project.ecommerce.Repositries;

import com.project.ecommerce.Entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AddressRepository extends CrudRepository<Address,Long> {
    @Query(value = "select * from address where customer_user_id=:customerUserId",nativeQuery =true )
    Set<Address> findAllAddressByCustomer(@Param("customerUserId")Long customerUserId);

    @Query(value = "select * from address where address_id=:addressId and customer_user_id=:userId",nativeQuery = true)
    Address findByUserIdAndAddressId(@Param("addressId")Long addressId,@Param("userId")Long userId);

    @Query(value = "select * from address where address_id=:addressId and seller_user_id=:userId",nativeQuery = true)
    Address findBySellerIdAndAddressId(@Param("addressId")Long addressId,@Param("userId")Long userId);

    @Query(value = "select * from address where seller_user_id=:id",nativeQuery = true)
    Address findAddressBySeller(@Param("id") Long id);
}
