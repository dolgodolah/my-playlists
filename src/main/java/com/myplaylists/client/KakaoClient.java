package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KakaoClient {
    private static final Gson GSON = new Gson();
    private static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO = "https://kapi.kakao.com/v2/user/me";

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${kakao.login.redirect-uri}")
    private String redirectURI;

    /**
     * 카카오 로그인 정보로부터 유저 정보를 가져온다.
     */
    public KakaoOauthDto getKakaoUserInfo(String code) {
        try {
            Map<String, String> headers = Map.of(
                    HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(code),
                    HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8"
            );

            String response = AsyncHttpClient.getWithHeaders(KAKAO_USER_INFO, headers);
            return GSON.fromJson(response, KakaoOauthDto.class);
        } catch (Exception e) {
            log.error("failed to request {}", KAKAO_USER_INFO);
            throw new ApiException("카카오 로그인에 실패했습니다.", 500);
        }
    }

    private String getAccessToken(String code) {
        try {
            String data = new StringBuilder()
                    .append("grant_type=").append("authorization_code")
                    .append("&client_id=").append(kakaoApiKey)
                    .append("&redirect_uri=").append(redirectURI)
                    .append("&client_secret=").append(kakaoClientSecret)
                    .append("&code=").append(code)
                    .toString();

            String response = AsyncHttpClient.post(KAKAO_ACCESS_TOKEN, data, Map.of(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8"));
            return GSON.fromJson(response, HashMap.class).get("access_token").toString();
        } catch (Exception e) {
            log.error("failed to request {}", KAKAO_ACCESS_TOKEN);
            throw new ApiException("카카오 액세스 토큰 발급을 실패했습니다.", 500);
        }
    }
}
