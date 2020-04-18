package com.project.ecommerce.Repositries;

import com.project.ecommerce.Entities.CategoryMetadataField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long> {

    @Query(value = "from CategoryMetadataField")
    List<CategoryMetadataField> viewAllFields();
}
