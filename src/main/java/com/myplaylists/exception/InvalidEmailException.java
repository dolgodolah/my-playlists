package com.myplaylists.exception;

public class InvalidEmailException extends ApiException{

    public InvalidEmailException(String message) {
        super(message, 410);
    }
}
