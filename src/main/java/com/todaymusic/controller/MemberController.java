package com.todaymusic.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.todaymusic.domain.Member;
import com.todaymusic.domain.Playlist;
import com.todaymusic.dto.MemberForm;
import com.todaymusic.repository.MemberRepository;
import com.todaymusic.service.MemberService;
import com.todaymusic.service.PlaylistService;

@Controller
public class MemberController {
	
	private MemberService memberService;
	private PlaylistService playlistService;
	private HttpSession session;
	
	@Autowired
	public MemberController(MemberService memberService, PlaylistService playlistService, HttpSession session) {
		this.memberService = memberService;
		this.playlistService=playlistService;
		this.session=session;
	}
	
	@GetMapping("/")
	public String main(Model model) {
		MemberForm loginMember = (MemberForm) session.getAttribute("MEMBER");
		if (loginMember != null) {
			return "redirect:/mylist";
		}
		model.addAttribute("memberForm", new MemberForm());
		return "member/login";
	}

	
	@GetMapping("/login")
	public String login(Model model) {
		MemberForm loginMember = (MemberForm) session.getAttribute("MEMBER");
		if (loginMember != null) {
			return "playlist/mylist";
		}
		model.addAttribute("memberForm",new MemberForm());
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@Valid MemberForm memberForm, BindingResult result, Model model) {
		/*
		 * 입력하지 않은 칸이 존재할 때 입력을 요구하는 에러메세지 출력
		 */
		if (result.hasErrors()) {
			return "member/login";
		}
		
		
		/*
		 * memberForm에서 받은 데이터로 member 생성
		 */
		Member member = new Member();
		member.setUsername(memberForm.getUsername());
		member.setPassword(memberForm.getPassword());
		
		
		/*
		 * 입력받은 member 정보가 올바르지 않을 때 가입되지 않은 유저명이거나 잘못된 비밀번호라는 에러메세지 출력
		 */
		if (memberService.login(member)==false) {
			model.addAttribute("inval", "가입되지 않은 유저이거나, 잘못된 비밀번호입니다.");
			return "member/login";
		}else {
			session.setAttribute("MEMBER", memberForm);
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/signup")
	public String join(Model model) {
		model.addAttribute("member", new Member());
		return "member/signup";
	}
	
	@PostMapping("/signup")
	public String join(@Valid Member member, BindingResult result, Model model) {
		/*
		 * 입력받은 정보가 형식에 맞지 않을 때 입력 형식을 나타내는 에러메세지 출력
		 */
		if (result.hasErrors()) {
			return "member/signup";
		}
		
		try {
			memberService.join(member);
		}catch (Exception e) { // 이미 존재하는 유저명이라면 에러메세지 출력
			model.addAttribute("inval", e.getMessage());
			return "member/signup";
		}
		
		/*
		 * 회원가입 성공
		 */
		return "redirect:/";
	}
	
	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/";
	}
	
}
