package com.project.ecommerce.repository;

import com.project.ecommerce.entity.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory,Long> {

    Optional<ProductCategory> findByName(String name);

    @Query(value = "from ProductCategory")
    List<ProductCategory> getAll(Pageable pageable);

    @Query(value = "select * from product_category where parent_id is null",nativeQuery = true)
    List<ProductCategory> getAllRoot();

    @Query(value = "select distinct c1.pc_id, " +
            "c1.name, c1.parent_id " +
            "from product_category c1," +
            "product_category c2 " +
            "where c1.parent_id=c2.parent_id " +
            "and c1.parent_id=:id",nativeQuery = true)
    List<ProductCategory> getAllChild(@Param("id") Long id);
}
