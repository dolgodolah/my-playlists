package com.myplaylists.exception;

public class InvalidNicknameException extends ApiException {

    private static final String DEFAULT_MESSAGE = "닉네임이 공백이거나 입력되지 않았습니다.";

    public InvalidNicknameException() {
        super(DEFAULT_MESSAGE, 411);
    }

    public InvalidNicknameException(String message) {
        super(message, 411);
    }
}
