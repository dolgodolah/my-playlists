package com.todaymusic.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.todaymusic.domain.Member;
import com.todaymusic.domain.Playlist;
import com.todaymusic.service.MemberService;
import com.todaymusic.service.PlaylistService;

@RestController
public class MemberController {
	
	private MemberService memberService;
	private PlaylistService playlistService;
	
	@Autowired
	public MemberController(MemberService memberService, PlaylistService playlistService) {
		this.memberService = memberService;
		this.playlistService=playlistService;
	}

	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("/member/login");
		return mav;
	}
	
	@PostMapping("/login")
	public ModelAndView login(Model model) {
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
	@GetMapping("/join")
	public String join() {
		
		return "회원가입";
	}
	
}
