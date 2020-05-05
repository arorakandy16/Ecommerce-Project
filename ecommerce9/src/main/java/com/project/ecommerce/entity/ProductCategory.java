package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.auditing.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductCategory extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pcId;


    @NotNull
    @Column(unique = true)
//    @Pattern(regexp = "^[a-zA-Z]{1,50}$",
//     message = "field category name can only contains alphabets")
    private String name;


    @OneToMany(mappedBy = "productcategory",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products;


    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductCategory> leafCategory;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parentCategory;


    public ProductCategory(String name) {
        this.name=name;
    }


    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> fieldValues;



    public ProductCategory(){ }


    //-------------------------------------------------------------------------------------------
    public void addLeafCategory(ProductCategory category){
        if(category != null){
            if(leafCategory == null)
                leafCategory = new HashSet<>();

            leafCategory.add(category);
            category.setParentCategory(this);
        }
    }

    //-------------------------------------------------------------------------------------------

    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new HashSet<Product>();

            products.add(product);

            product.setProductcategory(this);
        }
    }

    //-------------------------------------------------------------------------------------------

    public void addFieldValues(CategoryMetadataFieldValues fieldValue){
        if(fieldValue != null){
            if(fieldValues==null)
                fieldValues = new HashSet<>();

            fieldValues.add(fieldValue);
            fieldValue.setProductCategory(this);

        }
    }
    //--------------------------------------------------------------------------------------------


    public Set<ProductCategory> getLeafCategory() {
        return leafCategory;
    }

    public void setLeafCategory(Set<ProductCategory> leafCategory) {
        this.leafCategory = leafCategory;
    }

    public ProductCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ProductCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Long getPcId() {
        return pcId;
    }

    public void setPcId(Long pcId) {
        this.pcId = pcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CategoryMetadataFieldValues> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Set<CategoryMetadataFieldValues> fieldValues) {
        this.fieldValues = fieldValues;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "pcId=" + pcId +
                ", name='" + name + '\'' +
                '}';
    }
}
