package com.project.ecommerce.exceptions;

public class ServerSideException extends RuntimeException{
    public ServerSideException(String message) {
        super(message);
    }
}
