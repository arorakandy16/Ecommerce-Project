package com.project.ecommerce.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
