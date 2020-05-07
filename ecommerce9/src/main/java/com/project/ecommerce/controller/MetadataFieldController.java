package com.project.ecommerce.controller;

import com.project.ecommerce.dto.MetadataFieldDto;
import com.project.ecommerce.entity.CategoryMetadataField;
import com.project.ecommerce.entity.CategoryMetadataFieldValues;
import com.project.ecommerce.service.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
public class MetadataFieldController {

    @Autowired
    MetadataFieldService metadataFieldService;



    @PostMapping("/admin/add/metadata/field")
    public String addMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField,
                                   @RequestHeader(name = "Accept-Language",
                                           required = false) Locale locale){
        return metadataFieldService.addField(categoryMetadataField,locale);
    }



    @GetMapping("/admin/field/view/all")
    public List<MetadataFieldDto> viewFields
            (@RequestHeader(name = "offset",
                    required = false) Integer offset,
             @RequestHeader(name = "size",
                     required = false) Integer size){
        return metadataFieldService.viewAllFields(offset,size);
    }



    @PostMapping("/admin/add/metadata/values/{categoryId}/{fieldId}")
    public String addMetadataWithValues
            (@RequestBody CategoryMetadataFieldValues values,
             @PathVariable Long categoryId, @PathVariable Long fieldId,
             @RequestHeader(name = "Accept-Language",
                     required = false) Locale locale){
        return metadataFieldService.addValues(values,categoryId,fieldId,locale);
    }



    @PutMapping("/admin/update/metadata/values/{categoryId}/{fieldId}")
    public String updateMetadataWithValues(@RequestBody CategoryMetadataFieldValues values,
                                           @PathVariable Long categoryId,
                                           @PathVariable Long fieldId,
                                           @RequestHeader(name = "Accept-Language",
                                                   required = false) Locale locale){
        return metadataFieldService.updateValues(values,categoryId,fieldId,locale);
    }
}
