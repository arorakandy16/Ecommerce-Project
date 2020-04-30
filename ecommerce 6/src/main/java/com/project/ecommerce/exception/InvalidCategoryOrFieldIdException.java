package com.project.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCategoryOrFieldIdException extends RuntimeException {
    public InvalidCategoryOrFieldIdException(String message) {
        super(message);
    }
}
