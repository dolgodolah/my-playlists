package com.myplaylists.controller

import com.myplaylists.dto.BaseResponse
import com.myplaylists.dto.auth.OauthRequest
import com.myplaylists.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login/kakao")
    fun login(@RequestBody oauthRequest: OauthRequest, session: HttpSession): BaseResponse {
        val loginUser = authService.authenticate(oauthRequest)
        session.setAttribute("user", loginUser)
        return BaseResponse.ok()
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): BaseResponse {
        session.invalidate()
        return BaseResponse.ok()
    }
}