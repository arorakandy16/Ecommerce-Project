package com.project.ecommerce.service;

import com.project.ecommerce.dto.MetadataFieldDto;
import com.project.ecommerce.entity.CategoryMetadataField;
import com.project.ecommerce.entity.CategoryMetadataFieldValues;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.exception.InvalidCategoryOrFieldIdException;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.repository.MetadataFieldRepository;
import com.project.ecommerce.repository.MetadataFieldValuesRepository;
import com.project.ecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MetadataFieldService {

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    @Autowired
    private MessageSource messageSource;


    //View All Fields

    public List<MetadataFieldDto> viewAllFields() {
        List<CategoryMetadataField> categoryMetadataField
                = metadataFieldRepository.viewAllFields
                (PageRequest.of(0,10, Sort.Direction.ASC,"id"));
        List<MetadataFieldDto> metadataFieldDtos=new ArrayList<>();

        for(CategoryMetadataField fields:categoryMetadataField){
            MetadataFieldDto metadataFieldDto=new MetadataFieldDto();
            metadataFieldDto.setId(fields.getId());
            metadataFieldDto.setName(fields.getName());
            metadataFieldDtos.add(metadataFieldDto);
        }
        return metadataFieldDtos;
    }


    //Add Field

    public String addField(CategoryMetadataField categoryMetadataField, Locale locale) {
        try {
            metadataFieldRepository.save(categoryMetadataField);
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException("Field name already exists");
        }
        throw new Message(messageSource.getMessage("admin.add.metadata.field.message", null, locale));
    }



    //Add Values

    public String addValues(CategoryMetadataFieldValues values,
                            Long categoryId, Long fieldId, Locale locale) {

        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);

        try {
            ProductCategory productCategory1=productCategory.get();
            CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();
            CategoryMetadataFieldValues categoryMetadataFieldValues
                    =new CategoryMetadataFieldValues(values.getMetadataValues());
            categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField1);
            categoryMetadataFieldValues.setProductCategory(productCategory1);
             metadataFieldValuesRepository.save(categoryMetadataFieldValues);
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException
                    ("Invalid Product Category Id or Metadata Field Id");
        }
        throw new Message(messageSource.getMessage("admin.add.metadata.values.message", null, locale));
    }


    //Update Values

    public String updateValues(CategoryMetadataFieldValues values, Long categoryId, Long fieldId, Locale locale) {
        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);
        try {
            ProductCategory productCategory1=productCategory.get();
            CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();
            metadataFieldValuesRepository.updateMetadataValues
                    (values.getMetadataValues(),categoryMetadataField1.getId(),productCategory1.getPcId());
        }
        catch (Exception ex){
            throw new InvalidCategoryOrFieldIdException("Invalid Product Category Id or Metadata Field Id");
        }
        throw new Message(messageSource.getMessage
                ("admin.update.metadata.values.message", null, locale));
    }
}