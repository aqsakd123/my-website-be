package com.example.my_website_pro.Config;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AppException(String message) {
        super(message);
        this.status = HttpStatus.OK;
    }

    public HttpStatus getStatus() {
        return status;
    }

}