package com.myplaylists.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.myplaylists.config.auth.dto.SessionUser;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.UserService;

@Controller
public class UserController {
	

	private final HttpSession session;
	private final UserService userService;
	private final PlaylistService playlistService;
	
	@Autowired
	public UserController(HttpSession session, UserService userService, PlaylistService playlistService) {
		this.session=session;
		this.userService = userService;
		this.playlistService=playlistService;
	}
	
	@GetMapping("/")
	public String main(Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user != null) {
			return "redirect:/mylist";
		}
		return "index";
	}
	
	@GetMapping("/myinfo")
	public String myinfo(Model model, @PageableDefault(size=6, sort="updatedAt", direction=Sort.Direction.DESC)Pageable pageable) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user != null) {
			model.addAttribute("playlists", playlistService.findMyPlaylists(pageable, userService.findUser(user)));
			
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			
			model.addAttribute("user", userService.findUser(user));
			return "user/myinfo";
		}
		return "index";
	}
	
	@PostMapping("/myinfo")
	public String updateMyinfo(String nickname) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		userService.updateUser(user,nickname);
		return "redirect:/mylist";
	}


	
//	@PostMapping("/login")
//	public String login(@Valid MemberForm memberForm, BindingResult result, Model model) {
//		/*
//		 * 입력하지 않은 칸이 존재할 때 입력을 요구하는 에러메세지 출력
//		 */
//		if (result.hasErrors()) {
//			return "member/login";
//		}
//		
//		
//		/*
//		 * memberForm에서 받은 데이터로 member 생성
//		 */
//		Member member = new Member();
//		member.setName(memberForm.getName());
//		
//		
//		/*
//		 * 입력받은 member 정보가 올바르지 않을 때 가입되지 않은 유저명이거나 잘못된 비밀번호라는 에러메세지 출력
//		 */
//		if (memberService.login(member)==false) {
//			model.addAttribute("inval", "가입되지 않은 유저이거나, 잘못된 비밀번호입니다.");
//			return "member/login";
//		}else {
//			session.setAttribute("MEMBER", memberForm);
//			return "redirect:/";
//		}
//		
//	}

	
	
}
