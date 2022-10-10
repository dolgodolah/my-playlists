package com.myplaylists.controller

import com.myplaylists.dto.BaseResponse
import com.myplaylists.dto.oauth.OauthDto
import com.myplaylists.service.AuthService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody oauthDto: OauthDto, session: HttpSession): BaseResponse {
        val loginUser = authService.authenticate(oauthDto)
        session.setAttribute("user", loginUser)
        return BaseResponse.ok()
    }

    @GetMapping("/login/kakao")
    fun loginKakao(@RequestParam code: String): OauthDto {
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