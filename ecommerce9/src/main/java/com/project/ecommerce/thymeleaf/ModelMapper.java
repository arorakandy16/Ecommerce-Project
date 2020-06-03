package com.project.ecommerce.thymeleaf;

import org.springframework.stereotype.Component;

@Component
public class ModelMapper extends org.modelmapper.ModelMapper {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}