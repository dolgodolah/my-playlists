package com.myplaylists.exception;

public class InvalidNicknameException extends ApiException {

    public InvalidNicknameException(String message) {
        super(message, 411);
    }
}
