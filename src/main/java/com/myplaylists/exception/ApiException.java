package com.myplaylists.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }
}
