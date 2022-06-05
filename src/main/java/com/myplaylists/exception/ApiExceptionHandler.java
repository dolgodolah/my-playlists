package com.myplaylists.exception;

import com.myplaylists.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseDto exceptionHandler(final ApiException e) {
        return ResponseDto.of(e.getStatusCode(), ErrorResponse.of(e.getMessage()));
    }
}
