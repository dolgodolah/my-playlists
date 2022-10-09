package com.myplaylists.service;

import com.google.gson.Gson;
import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.auth.OauthRequest;
import com.myplaylists.dto.oauth.KakaoAccount;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.exception.ApiException;
import com.myplaylists.exception.InvalidEmailException;
import com.myplaylists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private static final String KAKAO_ACCESS_TOKEN = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO = "https://kapi.kakao.com/v2/user/me";
    private static final String REDIRECT_URI = "http://localhost:3000/login/kakao";

    private final UserRepository userRepository;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    /**
     * 내플리스 로그인 처리
     */
    public LoginUser authenticate(OauthRequest oauthRequest) {
        User user = signUpOrLogin(oauthRequest);
        return new LoginUser(user);
    }

    /**
     * 이미 존재하는 이메일이면 최신 정보로 업데이트 후 로그인 처리,
     * 처음 로그인하는 이메일이면 회원 가입 후 로그인 처리
     */
    private User signUpOrLogin(OauthRequest oauthRequest) {
        if (!StringUtils.hasText(oauthRequest.getEmail())) {
            throw new InvalidEmailException();
        }

        User user = userRepository.findByEmail(oauthRequest.getEmail())
                .map(entity -> entity.updateName(oauthRequest.getName()))
                .orElse(oauthRequest.toEntity());

        return userRepository.save(user);
    }


    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    public KakaoAccount getKakaoUserInfo(String code) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(KAKAO_USER_INFO))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(code))
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), KakaoOauthDto.class).getKakaoAccount();
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
