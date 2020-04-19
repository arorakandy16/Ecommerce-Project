package com.project.ecommerce.repository;

import com.project.ecommerce.entity.ProductVariant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariant,Long> {
    @Query(value = "select * from product_variant where product_id =:productId",nativeQuery = true)
    List<ProductVariant> findByProductId(@Param("productId") Long productId);
}
