package com.myplaylists.controller;

import com.myplaylists.dto.KakaoToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @PostMapping("/login/kakao")
    public void login(@RequestBody KakaoToken kakaoToken, HttpServletResponse res) {
        Cookie cookie = new Cookie("accessToken", kakaoToken.getAccessToken());
        res.addCookie(cookie);
    }
}
