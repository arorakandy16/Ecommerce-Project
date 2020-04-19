package com.project.ecommerce.repository;

import com.project.ecommerce.entity.CategoryMetadataField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long> {

    @Query(value = "from CategoryMetadataField")
    List<CategoryMetadataField> viewAllFields();

    @Query(value = "from CategoryMetadataField where name=:field")
    CategoryMetadataField findByName(@Param("field") String field);
}
