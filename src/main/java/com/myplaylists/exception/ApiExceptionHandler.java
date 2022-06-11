package com.myplaylists.exception;

import com.myplaylists.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * `Exception`을 따로 처리하지 않으면 클라이언트에서는 HTTP status 500을 받게 된다.
     * `Exception`을 잡아 `ErrorResponse`를 반환해주고, (HTTP status 200)
     * 클라이언트에서는 `ErrorResponse`의 `statusCode`를 보고 예외 처리를 진행한다.
     */
    @ExceptionHandler(ApiException.class)
    public ErrorResponse exceptionHandler(final ApiException e) {
        return new ErrorResponse(e);
    }
}
