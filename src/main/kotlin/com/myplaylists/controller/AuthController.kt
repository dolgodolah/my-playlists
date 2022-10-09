package com.myplaylists.controller

import com.myplaylists.dto.BaseResponse
import com.myplaylists.dto.auth.OauthRequest
import com.myplaylists.dto.oauth.KakaoAccount
import com.myplaylists.service.AuthService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody oauthRequest: OauthRequest, session: HttpSession): BaseResponse {
        val loginUser = authService.authenticate(oauthRequest)
        session.setAttribute("user", loginUser)
        return BaseResponse.ok()
    }

    @GetMapping("/login/kakao")
    fun loginKakao(@RequestParam code: String): KakaoAccount {
        return authService.getKakaoUserInfo(code)
    }

//    @GetMapping("/login/google")
//    fun loginGoogle(@RequestParam code: String): GoogleAccount {
//        return authService.getGoogleUserInfo(code)
//    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): BaseResponse {
        session.invalidate()
        return BaseResponse.ok()
    }
}