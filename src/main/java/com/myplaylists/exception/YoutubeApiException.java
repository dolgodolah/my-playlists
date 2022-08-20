package com.myplaylists.exception;

public class YoutubeApiException extends ApiException {

    private static final String DEFAULT_MESSAGE = "유튜브 검색에 실패했습니다. 잠시 후 다시 시도해보세요.";

    public YoutubeApiException() {
        super(DEFAULT_MESSAGE, 450);
    }

    public YoutubeApiException(String message) {
        super(message, 450);
    }
}
