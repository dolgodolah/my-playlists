package com.myplaylists.controller;

import com.myplaylists.dto.BaseResponse;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.auth.OauthRequest;
import com.myplaylists.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/kakao")
    public BaseResponse login(@RequestBody OauthRequest oauthRequest, HttpSession session) {
        LoginUser loginUser = authService.authenticate(oauthRequest);
        session.setAttribute("user", loginUser);
        return BaseResponse.ok();
    }

    @PostMapping("/logout")
    public BaseResponse logout(HttpSession session) {
        session.invalidate();
        return BaseResponse.ok();
    }
}
