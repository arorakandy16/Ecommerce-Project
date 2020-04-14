package com.project.ecommerce.exception;

public class ServerSideException extends RuntimeException{
    public ServerSideException(String message) {
        super(message);
    }
}
