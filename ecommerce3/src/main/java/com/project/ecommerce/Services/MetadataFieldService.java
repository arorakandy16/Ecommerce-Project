package com.project.ecommerce.Services;

import com.project.ecommerce.Daos.MetadataFieldDto;
import com.project.ecommerce.Entities.CategoryMetadataField;
import com.project.ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.ecommerce.Entities.ProductCategory;
import com.project.ecommerce.Exceptions.InvalidCategoryOrFieldIdException;
import com.project.ecommerce.Repositries.MetadataFieldRepository;
import com.project.ecommerce.Repositries.MetadataFieldValuesRepository;
import com.project.ecommerce.Repositries.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MetadataFieldService {

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

//    @Autowired
//    MetadataFieldValuesRepository metadataFieldValuesRepository;

    public String addField(CategoryMetadataField categoryMetadataField) {
        try {
            metadataFieldRepository.save(categoryMetadataField);
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException("Field name already exists");
        }
        return "Field added successfully";
    }

    public String addValues(CategoryMetadataFieldValues values, Long categoryId, Long fieldId) {
        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);
        try {
            ProductCategory productCategory1=productCategory.get();
//            System.out.println(productCategory1.getName());
            CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();
            CategoryMetadataFieldValues categoryMetadataFieldValues=new CategoryMetadataFieldValues(values.getMetadataValues());
            categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField1);
            categoryMetadataFieldValues.setProductCategory(productCategory1);
             metadataFieldValuesRepository.save(categoryMetadataFieldValues);
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException("Invalid Product Category Id or Metadata Field Id");
        }
        return "Values added Successfully";
    }

    public String updateValues(CategoryMetadataFieldValues values, Long categoryId, Long fieldId) {
        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);
        try {
            ProductCategory productCategory1=productCategory.get();
            System.out.println("hii");
            CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();
            System.out.println("byee");
            metadataFieldValuesRepository.updateMetadataValues(values.getMetadataValues(),categoryMetadataField1.getId(),productCategory1.getPc_id());
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException("Invalid Product Category Id or Metadata Field Id");
        }
        return "Values updated Successfully";
    }

public List<MetadataFieldDto> viewAllFields() {
    List<CategoryMetadataField> categoryMetadataField = metadataFieldRepository.viewAllFields();
    List<MetadataFieldDto> metadataFieldDtos=new ArrayList<>();

    for(CategoryMetadataField fields:categoryMetadataField){
        MetadataFieldDto metadataFieldDto=new MetadataFieldDto();
        metadataFieldDto.setId(fields.getId());
        metadataFieldDto.setName(fields.getName());
        metadataFieldDtos.add(metadataFieldDto);
    }
    return metadataFieldDtos;
}
}
