package com.project.ecommerce.dto;

import java.io.Serializable;

public class MetadataFieldDto implements Serializable {

    private Long Id;

    private String name;

    public MetadataFieldDto() { }

    public MetadataFieldDto(Long id, String name) { }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
