package com.project.ecommerce.exception;

public class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
