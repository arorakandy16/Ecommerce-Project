package com.project.ecommerce.dto;

import java.io.Serializable;

public class CategoryDto implements Serializable {

    private Long pcId;

    private String name;

    public CategoryDto(Long pcId, String name) {
        this.pcId = pcId;
        this.name = name;
    }

    public CategoryDto() { }

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
}
