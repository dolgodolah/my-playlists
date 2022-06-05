package com.myplaylists.exception;

public class YoutubeApiException extends ApiException {

    public YoutubeApiException(String message) {
        super(message, 450);
    }
}
