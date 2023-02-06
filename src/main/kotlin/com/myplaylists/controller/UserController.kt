package com.myplaylists.controller

import com.myplaylists.config.auth.Login
import com.myplaylists.dto.UserDto
import com.myplaylists.dto.ViewResponse
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.context.MeViewContext
import com.myplaylists.service.PlaylistService
import com.myplaylists.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class UserController(
    private val userService: UserService,
    private val playlistService: PlaylistService,
) {

    /**
     * View Controller
     */
    @GetMapping("/me")
    fun meView(@Login loginUser: LoginUser): ResponseEntity<String> {
        val user = userService.findUserById(loginUser.userId)
        val playlistsDto = playlistService.findMyPlaylists(loginUser.userId)

        val context = MeViewContext(
            name = user.name,
            nickname = user.nickname,
            email = user.email,
            playlists = playlistsDto.playlists
        )

        return ViewResponse.ok().render("me.html", context = context)
    }

    /**
     * API Controller
     */
    @ResponseBody
    @PostMapping("/me")
    fun updateMyInfo(@Login user: LoginUser, @RequestBody userDto: UserDto): UserDto {
        return userService.updateUserInfo(user.userId, userDto)
    }
}