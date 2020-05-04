package com.project.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CategoryMetadataField {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(unique = true)
//    @Pattern(regexp = "^[a-zA-Z ]{2,50}$",message
//            = "field Category name can only contains alphabets")
    private String name;


    //--------------------------------------------------------------------------------------------
    public void addFieldValues(CategoryMetadataFieldValues fieldValue){
        if(fieldValue != null){
            if(fieldValues==null)
                fieldValues = new HashSet<>();

            fieldValues.add(fieldValue);
            fieldValue.setCategoryMetadataField(this);
        }
    }
    //--------------------------------------------------------------------------------------------


    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> fieldValues;

    public CategoryMetadataField(String name) {
        this.name=name;
    }

    public CategoryMetadataField(){ }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryMetadataFieldValues> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Set<CategoryMetadataFieldValues> fieldValues) {
        this.fieldValues = fieldValues;
    }



    @Override
    public String toString() {
        return "CategoryMetadataField{" +
                "id=" + id +
                ", fieldName='" + name + '\'' +
                '}';
    }
}
