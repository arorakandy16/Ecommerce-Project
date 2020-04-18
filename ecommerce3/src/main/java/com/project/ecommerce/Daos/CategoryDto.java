package com.project.ecommerce.Daos;

public class CategoryDto {
    private Long pc_id;
    private String name;

    public CategoryDto(Long pc_id, String name) {
        this.pc_id = pc_id;
        this.name = name;
    }

    public CategoryDto() {
    }

    public Long getPc_id() {
        return pc_id;
    }

    public void setPc_id(Long pc_id) {
        this.pc_id = pc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
