package com.myplaylists.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String message;

    public static ErrorResponse of(String message){
        return new ErrorResponse(message);
    }
}
