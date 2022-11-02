package com.myplaylists.controller

import com.myplaylists.dto.oauth.OauthDto
import com.myplaylists.service.AuthService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody oauthDto: OauthDto, session: HttpSession) {
        val signupUser = authService.signup(oauthDto)
        session.setAttribute("user", signupUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody oauthDto: OauthDto, session: HttpSession) {
        val loginUser = authService.login(oauthDto)
        session.setAttribute("user", loginUser)
    }

    @GetMapping("/login/kakao")
    fun loginKakao(@RequestParam code: String): OauthDto {
        return authService.getKakaoUserInfo(code)
    }

    @GetMapping("/login/google")
    fun loginGoogle(@RequestParam code: String): OauthDto {
        return authService.getGoogleUserInfo(code)
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession) {
        session.invalidate()
    }
}