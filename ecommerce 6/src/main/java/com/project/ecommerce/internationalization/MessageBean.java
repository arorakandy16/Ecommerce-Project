package com.project.ecommerce.internationalization;


public class MessageBean {

    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageBean(String message) {
        this.message=message;
    }


    @Override
    public String toString() {
        return "MessageBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
