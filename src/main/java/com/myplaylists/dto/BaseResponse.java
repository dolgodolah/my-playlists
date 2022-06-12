package com.myplaylists.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseResponse {
    private static final BaseResponse OK = new BaseResponse();

    private int statusCode;

    public BaseResponse() {
        this.statusCode = 200;
    }

    public static BaseResponse ok() {
        return OK;
    }
}
