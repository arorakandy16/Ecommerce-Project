package com.project.ecommerce.Controllers;

import com.project.ecommerce.Daos.MetadataFieldDto;
import com.project.ecommerce.Entities.CategoryMetadataField;
import com.project.ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.ecommerce.Services.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MetadataFieldController {

    @Autowired
    MetadataFieldService metadataFieldService;

    @PostMapping("/admin/add/metadata-field")
    public String addMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField){
        return metadataFieldService.addField(categoryMetadataField);
    }

    @GetMapping("/admin/field/*")
    public List<MetadataFieldDto> viewFields(){
        return metadataFieldService.viewAllFields();
    }

    @PostMapping("/admin/add/metadata-values/{categoryId}/{fieldId}")
    public String addMetadataWithValues(@RequestBody CategoryMetadataFieldValues values, @PathVariable Long categoryId, @PathVariable Long fieldId){
        return metadataFieldService.addValues(values,categoryId,fieldId);
    }

    @PutMapping("/admin/add/metadata-values/{categoryId}/{fieldId}")
    public String updateMetadataWithValues(@RequestBody CategoryMetadataFieldValues values, @PathVariable Long categoryId, @PathVariable Long fieldId){
        return metadataFieldService.updateValues(values,categoryId,fieldId);
    }

}
