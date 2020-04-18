package com.project.ecommerce.Repositries;

import com.project.ecommerce.Entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query(value = "select * from product where product_id=:productId and seller_user_id=:userId", nativeQuery = true)
    Optional<Product> findByIdAndSellerId(@Param("userId") Long userid, @Param("productId") Long productId);

    @Query(value = "select * from product where seller_user_id=:userId", nativeQuery = true)
    List<Product> findAllBySeller(@Param("userId") Long userId);

    @Query(value = "select * from product where product_id=:productId and seller_user_id=:userId", nativeQuery = true)
    Product findByUserIdAndSellerId(@Param("productId") Long productId, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from product where product_id=:productId and seller_user_id=:userId", nativeQuery = true)
    void deleteByIdAndSellerId(@Param("productId") Long productId, @Param("userId") Long userId);

    @Query(value = "select * from product where category_id=:categoryId", nativeQuery = true)
    List<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "update product set is_active=:is_active where product_id=:productId",nativeQuery = true)
    void deActivateProduct(@Param("productId") Long productId,@Param("is_active") boolean is_active);

    @Transactional
    @Modifying
    @Query(value = "update product set is_active=:is_active where product_id=:productId",nativeQuery = true)
    void activateProduct(@Param("productId") Long productId,@Param("is_active") boolean is_active);
}
