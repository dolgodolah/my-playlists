package com.myplaylists.controller;

import com.myplaylists.dto.ResponseDto;
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
    public ResponseDto login(@RequestBody OauthRequest oauthRequest, HttpSession session) {
        LoginUser loginUser = authService.authenticate(oauthRequest);
        session.setAttribute("user", loginUser);
        return ResponseDto.ok();
    }

    @PostMapping("/logout")
    public ResponseDto logout(HttpSession session) {
        session.invalidate();
        return ResponseDto.ok();
    }
}
