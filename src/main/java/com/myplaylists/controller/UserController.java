package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.UserDto;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	private final UserService userService;
	private final PlaylistService playlistService;
	
	@Autowired
	public UserController(UserService userService, PlaylistService playlistService) {
		this.userService = userService;
		this.playlistService=playlistService;
	}

	@GetMapping("/me")
	public String myInfo(@Login LoginUser user, Model model, @PageableDefault(size=6, sort="updatedDate", direction=Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("playlists", playlistService.findMyPlaylists(pageable, user.getId()));
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		return "user/myInfo";
	}

	@ResponseBody
	@GetMapping("/myInfo")
	public ResponseEntity<UserDto> getMyInfo(@Login LoginUser user) {
		return ResponseEntity.ok(userService.findByUserId(user.getId()));
	}

	@ResponseBody
	@PostMapping("/myInfo")
	public ResponseEntity<UserDto> updateMyInfo(@Login LoginUser user, @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.updateUserInfo(user.getId(), userDto));
	}
}
