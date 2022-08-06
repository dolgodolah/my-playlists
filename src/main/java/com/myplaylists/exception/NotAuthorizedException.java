package com.myplaylists.exception;

public class NotAuthorizedException extends ApiException {

    private static final String DEFAULT_MESSAGE = "로그인을 먼저 진행해주세요.";

    public NotAuthorizedException() {
        super(DEFAULT_MESSAGE, 401);
    }

    public NotAuthorizedException(String message) {
        super(message, 401);
    }
}
