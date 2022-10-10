package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
public class KakaoClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(KakaoClient.class);
    private static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO = "https://kapi.kakao.com/v2/user/me";
    private static final String REDIRECT_URI = "http://localhost:3000/login/kakao";

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    /**
     * 카카오 로그인 정보로부터 유저 정보를 가져온다.
     */
    public KakaoOauthDto getKakaoUserInfo(String code) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(KAKAO_USER_INFO))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(code))
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), KakaoOauthDto.class);
        } catch (Exception e) {
            throw new ApiException("카카오 로그인에 실패했습니다.", 500);
        }
    }

    private String getAccessToken(String code) throws Exception {
        try {
            String params = new StringBuilder()
                    .append("grant_type=authorization_code")
                    .append("&client_id=" + kakaoApiKey)
                    .append("&redirect_uri=" + REDIRECT_URI)
                    .append("&client_secret=" + kakaoClientSecret)
                    .append("&code=" + code)
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(KAKAO_ACCESS_TOKEN))
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), HashMap.class).get("access_token").toString();
        } catch (Exception e) {
            LOGGER.warn(KAKAO_ACCESS_TOKEN + " api error");
            throw e;
        }
    }
}
