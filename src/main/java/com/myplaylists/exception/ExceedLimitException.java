package com.myplaylists.exception;

public class ExceedLimitException extends ApiException {

    public ExceedLimitException(String message) {
        super(message, 460);
    }
}
