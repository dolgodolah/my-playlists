package com.myplaylists.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private String message;
    private int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
