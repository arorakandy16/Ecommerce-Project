package com.project.ecommerce.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryAlreadyRegistered extends RuntimeException {
    public CategoryAlreadyRegistered(String message) {
        super(message);    }
}
