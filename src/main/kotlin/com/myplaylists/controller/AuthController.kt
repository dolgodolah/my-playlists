package com.myplaylists.controller

import com.myplaylists.dto.ViewResponse
import com.myplaylists.dto.oauth.OauthType
import com.myplaylists.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class AuthController(
    private val authService: AuthService
) {

    /**
     * View Controller
     */
    @GetMapping("/login")
    fun loginView(): ResponseEntity<String> {
        return ViewResponse.ok().render("login.html")
    }

    @GetMapping("/login/kakao")
    fun loginKakao(@RequestParam code: String, session: HttpSession): ResponseEntity<String> {
        val loginUser = authService.login(code, OauthType.KAKAO)
        session.setAttribute("user", loginUser)

        return ViewResponse.redirect().to("/playlists")
    }

    @GetMapping("/login/google")
    fun loginGoogle(@RequestParam code: String, session: HttpSession): ResponseEntity<String> {
        val loginUser = authService.login(code, OauthType.GOOGLE)
        session.setAttribute("user", loginUser)

        return ViewResponse.redirect().to("/playlists")
    }


    /**
     * API Controller
     */
    @ResponseBody
    @PostMapping("/logout")
    fun logout(session: HttpSession) {
        session.invalidate()
    }
}