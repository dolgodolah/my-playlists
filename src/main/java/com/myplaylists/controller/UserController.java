package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.UserDto;
import com.myplaylists.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/my-info")
	public UserDto getMyInfo(@Login LoginUser user) {
		return userService.findUserById(user.getUserId());
	}

	@PostMapping("/my-info")
	public UserDto updateMyInfo(@Login LoginUser user, @RequestBody UserDto userDto) {
		return userService.updateUserInfo(user.getUserId(), userDto);
	}
}
