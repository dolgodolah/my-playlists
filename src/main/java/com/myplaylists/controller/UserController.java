package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.UserDto;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/my-info")
	public ResponseEntity<UserDto> getMyInfo(@Login LoginUser user) {
		return ResponseEntity.ok(userService.findByUserId(user.getId()));
	}

	@PostMapping("/my-info")
	public ResponseEntity<UserDto> updateMyInfo(@Login LoginUser user, @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.updateUserInfo(user.getId(), userDto));
	}
}
