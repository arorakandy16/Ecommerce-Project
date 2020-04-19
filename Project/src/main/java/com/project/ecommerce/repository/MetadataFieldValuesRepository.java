package com.project.ecommerce.repository;

import com.project.ecommerce.entity.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues,Long>{

    @Transactional
    @Modifying
    @Query(value = "update category_metadata_field_values set metadata_values=:value where  category_id=:productCategory and category_metadata_field_id=:categoryMetadataField ",nativeQuery = true)
    void updateMetadataValues(@Param("value") String value,@Param("categoryMetadataField") Long categoryMetadataField,@Param("productCategory") Long productCategory);

    @Query(value = "select * from category_metadata_field_values where category_metadata_field_id=:fieldId AND category_id=:categoryId ",nativeQuery = true)
    CategoryMetadataFieldValues findMetadataFieldValue(@Param("fieldId")Long fieldId,@Param("categoryId")Long categoryId);
}