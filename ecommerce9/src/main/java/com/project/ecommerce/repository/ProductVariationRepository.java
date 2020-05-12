package com.project.ecommerce.repository;

import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductVariant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariant,Long> {


    @Query(value = "select * from product_variant " +
            "where product_id =:productId",nativeQuery = true)
    List<ProductVariant> findByProductId
            (@Param("productId") Long productId, Pageable pageable);

    @Query(value = "select p.product_name, " +
            "u.email from product as p, " +
            "product_variant as pv, " +
            "user as u where p.product_id=pv.product_id " +
            "and " +
            "p.seller_user_id=u.userid " +
            "and" +
            " pv.quantity_available<5",
            nativeQuery = true)
    List<Object[]> findByProductQuantity();

//    @Query(value = "select * from product_variant where quantity_available<5", nativeQuery = true)
//    List<ProductVariant> findByProductQuantity();


//    @Query(value = "select " +
//            "p.brand,p.product_name,p.seller_user_id," +
//            "pv.price,pv.meta_data,pv.quantity_available " +
//            "from product as p INNER JOIN product_variant as pv on " +
//            "p.product_id=pv.product_variation_id " +
//            "where pv.quantity_available<5",
//            nativeQuery = true)
//    List<ProductVariant> findByProductQuantity();


    @Query(value = "select * from product_variant where product_id =:productId",nativeQuery = true)
    List<ProductVariant> findByProductIdWithoutPaging
            (@Param("productId") Long productId);

}
