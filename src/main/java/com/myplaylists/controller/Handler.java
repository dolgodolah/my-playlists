package com.myplaylists.controller;

import com.myplaylists.dto.BaseResponse;
import com.myplaylists.dto.ErrorResponse;
import com.myplaylists.exception.ApiException;
import com.myplaylists.exception.AuthRequiredException;
import com.myplaylists.exception.BadRequestException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

@RestControllerAdvice
public class Handler implements ResponseBodyAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse exceptionHandler(final MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        if (error == null || error.getDefaultMessage() == null) {
            return new ErrorResponse(new BadRequestException());
        }

        String errorMessage = error.getDefaultMessage();
        BadRequestException badRequestException = new BadRequestException(errorMessage);
        return new ErrorResponse(badRequestException);
    }

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<String> redirectToLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));

        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * 현재 사용 중인 axios 클라이언트는 `error handling`을 하지 못한다.
     * `Exception`을 잡아 `ErrorResponse`를 반환해주고, (HTTP status 200)
     * 클라이언트에서는 `ErrorResponse`의 `statusCode`를 보고 예외 처리를 진행한다.
     */
    @ExceptionHandler(ApiException.class)
    public ErrorResponse exceptionHandler(final ApiException e) {
        return new ErrorResponse(e);
    }


    /**
     *  클라이언트에서는 커스텀한 `statusCode`를 봐야하기 때문에
     *  API `return type`이 `void`일 경우 기본 응답값 객체(BaseResponse)를 내려준다.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethod().getReturnType() == Void.TYPE;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return BaseResponse.ok();
    }
}
