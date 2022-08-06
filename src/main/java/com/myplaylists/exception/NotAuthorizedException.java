package com.myplaylists.exception;

public class NotAuthorizedException extends ApiException {

    public NotAuthorizedException(String message) {
        super(message, 401);
    }
}
