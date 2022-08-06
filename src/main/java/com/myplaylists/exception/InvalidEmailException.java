package com.myplaylists.exception;

public class InvalidEmailException extends ApiException {

    private static final String DEFAULT_MESSAGE = "이메일은 필수입니다. 카카오로 로그인 시 이메일 제공에 동의하지 않았다면 카카오 계정 > 연결된 서비스 관리 > 내플리스 > 연결 끊기 후 이메일 제공에 동의해주세요.";

    public InvalidEmailException() {
        super(DEFAULT_MESSAGE, 410);
    }

    public InvalidEmailException(String message) {
        super(message, 410);
    }
}
