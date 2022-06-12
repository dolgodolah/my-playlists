package com.myplaylists.dto;

import com.myplaylists.exception.ApiException;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(ApiException e) {
        this.statusCode = e.getStatusCode();
        this.message = e.getMessage();
    }
}
