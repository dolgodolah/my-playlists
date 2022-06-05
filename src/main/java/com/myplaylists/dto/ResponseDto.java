package com.myplaylists.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto {

    private static final ResponseDto OK = new ResponseDto(HttpStatus.OK.value());

    private int statusCode;
    private Object body;

    public ResponseDto(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseDto(Object body) {
        this.statusCode = HttpStatus.OK.value();
        this.body = body;
    }

    public ResponseDto(int statusCode, Object body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public static ResponseDto ok() {
        return OK;
    }

    public static ResponseDto of(Object body) {
        return new ResponseDto(body);
    }

    public static ResponseDto of(int statusCode, Object body) {
        return new ResponseDto(statusCode, body);
    }
}
