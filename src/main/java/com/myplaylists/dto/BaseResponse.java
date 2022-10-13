package com.myplaylists.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseResponse {
    private static final BaseResponse OK = new BaseResponse();

    private int statusCode;

    // 응답 DTO 들이 상속받아야 해서 public 으로 열어둠.
    public BaseResponse() {
        this.statusCode = 200;
    }

    public static BaseResponse ok() {
        return OK;
    }
}
