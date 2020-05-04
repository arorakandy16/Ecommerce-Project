package com.project.ecommerce.repository;

import com.project.ecommerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query(value = "select * from product " +
            "where product_id=:productId and seller_user_id=:userId", nativeQuery = true)
    Optional<Product> findByIdAndSellerId
            (@Param("userId") Long userId, @Param("productId") Long productId);

    @Query(value = "select * from product " +
            "where seller_user_id=:userId and is_deleted=false", nativeQuery = true)
    List<Product> findAllBySeller
            (@Param("userId") Long userId , Pageable pageable);

    @Query(value = "select * from product " +
            "where category_id=:categoryId and is_deleted = false " +
            "and is_active=true", nativeQuery = true)
    List<Product> findAllByCategoryIdForCustomerAdmin
            (@Param("categoryId") Long categoryId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Product set is_active=:is_active where productId=:productId")
    void isActivateProduct(@Param("productId") Long productId,
                           @Param("is_active") boolean is_active);

    @Transactional
    @Modifying
    @Query(value = "update Product set is_deleted " +
            "= true where productId=:id")
    void deleteProduct(@Param("id") Long id);
}
