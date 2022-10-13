package com.myplaylists.controller;

import com.myplaylists.dto.BaseResponse;
import com.myplaylists.dto.ErrorResponse;
import com.myplaylists.exception.ApiException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class Handler implements ResponseBodyAdvice {

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
