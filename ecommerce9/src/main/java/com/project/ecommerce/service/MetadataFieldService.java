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

    public List<MetadataFieldDto> viewAllFields(Integer offset,Integer size) {

        if (offset==null)
            offset=0;

        if (size==null)
            size=10;

        List<CategoryMetadataField> categoryMetadataField
                = metadataFieldRepository.viewAllFields
                (PageRequest.of(offset,size, Sort.Direction.ASC,"id"));

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
        throw new Message(messageSource.getMessage
                ("admin.add.metadata.field.message", null, locale));
    }



    //Add Values

    public String addValues(CategoryMetadataFieldValues values,
                            Long categoryId, Long fieldId, Locale locale) {

        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);

        if(!productCategory.isPresent())
            throw new InvalidCategoryOrFieldIdException("Category id is invalid");

        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);

        if(!categoryMetadataField.isPresent())
            throw new InvalidCategoryOrFieldIdException("Field id is invalid");

        ProductCategory productCategory1=productCategory.get();
        CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();
        CategoryMetadataFieldValues categoryMetadataFieldValues=
                new CategoryMetadataFieldValues(values.getMetadataValues());
        categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField1);
        categoryMetadataFieldValues.setProductCategory(productCategory1);

        metadataFieldValuesRepository.save(categoryMetadataFieldValues);
        throw new Message(messageSource.getMessage
                ("admin.add.metadata.values.message", null, locale));
    }



    //Update Values

    public String updateValues(CategoryMetadataFieldValues values,
                               Long categoryId, Long fieldId, Locale locale) {
        Optional<ProductCategory> productCategory=productCategoryRepository.findById(categoryId);

        if(!productCategory.isPresent())
            throw new InvalidCategoryOrFieldIdException("Category id is invalid");

        Optional<CategoryMetadataField> categoryMetadataField=metadataFieldRepository.findById(fieldId);

        if(!categoryMetadataField.isPresent())
            throw new InvalidCategoryOrFieldIdException("Field id is invalid");

        Optional<CategoryMetadataFieldValues> categoryMetadataFieldValues =
                metadataFieldValuesRepository.findByCategoryAndFieldId(categoryId,fieldId);

        if (!categoryMetadataFieldValues.isPresent())
            throw new InvalidCategoryOrFieldIdException
                    ("There is no value to update for this category and field id");

        ProductCategory productCategory1=productCategory.get();
        CategoryMetadataField categoryMetadataField1=categoryMetadataField.get();

        metadataFieldValuesRepository.updateMetadataValues
                (values.getMetadataValues(),
                        categoryMetadataField1.getId(),productCategory1.getPcId());
        throw new Message(messageSource.getMessage
                ("admin.update.metadata.values.message", null, locale));
    }


}