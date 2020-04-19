package com.project.ecommerce.dto;

import com.project.ecommerce.entity.CategoryMetadataFieldValues;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Set;

public class FilterCategoryDto {
    @ElementCollection
    private List<CategoryMetadataFieldValues> categoryFieldValues;
    @ElementCollection
    private Set<String> brandsList;
    private Integer maxPrice=0;
    private Integer minPrice=0;

    public List<CategoryMetadataFieldValues> getCategoryFieldValues() {
        return categoryFieldValues;
    }

    public void setCategoryFieldValues(List<CategoryMetadataFieldValues> categoryFieldValues) {
        this.categoryFieldValues = categoryFieldValues;
    }

    public Set<String> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(Set<String> brandsList) {
        this.brandsList = brandsList;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }
}
