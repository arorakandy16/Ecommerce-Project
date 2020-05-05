package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.auditing.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CategoryMetadataFieldValues extends Auditable<String> implements Serializable {

        @JsonIgnore
        @EmbeddedId
        private MetadataFieldValueId id=new MetadataFieldValueId();


        @ManyToOne
        @JsonIgnore
        @MapsId("categoryMetadataFieldId")
        @JoinColumn(name = "categoryMetadataFieldId")
        private CategoryMetadataField categoryMetadataField;


        @ManyToOne
        @MapsId("categoryId")
        @JoinColumn(name = "categoryId")
        @JsonIgnore
        private ProductCategory productCategory;


        private String metadataValues;

        public CategoryMetadataFieldValues(String metadataValues) {

                this.metadataValues=metadataValues;
        }

        public CategoryMetadataFieldValues(){

        }

        public CategoryMetadataFieldValues(MetadataFieldValueId id, String value,
                                           CategoryMetadataField categoryMetadataField,
                                           ProductCategory category) {

                this.id = id;
                this.metadataValues = value;
                this.categoryMetadataField = categoryMetadataField;
                this.productCategory = category;
        }



        public CategoryMetadataField getCategoryMetadataField() {
                return categoryMetadataField;
        }

        public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
                this.categoryMetadataField = categoryMetadataField;
        }

        public ProductCategory getProductCategory() {
                return productCategory;
        }

        public void setProductCategory(ProductCategory productCategory) {
                this.productCategory = productCategory;
        }

        public String getMetadataValues() {
                return metadataValues;
        }

        public void setMetadataValues(String metadataValues) {
                this.metadataValues = metadataValues;
        }

        public MetadataFieldValueId getId() {
                return id;
        }

        public void setId(MetadataFieldValueId id) {
                this.id = id;
        }
}
