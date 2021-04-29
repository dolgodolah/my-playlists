package com.todaymusic.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
public class PlaylistController {
	
	PlaylistService playlistService;
	MemberService memberService;
	MemberRepository memberRepository;
	
	@Autowired
	public PlaylistController(PlaylistService playlistService, MemberService memberService, MemberRepository memberRepository) {
		this.playlistService = playlistService;
		this.memberService = memberService;
		this.memberRepository = memberRepository;
	}
	
	@GetMapping("/mylist/add")
	public String add(HttpSession session, Model model) {
		MemberForm loginMember = (MemberForm) session.getAttribute("MEMBER");
		if (loginMember!=null) {
			model.addAttribute("playlist", new Playlist());
			return "playlist/addlist";
		}
		model.addAttribute("memberForm", new MemberForm());
		return "member/login";
		
	}
	
	@Transactional
	@PostMapping("/mylist/add")
	public String add(HttpSession session, Playlist playlist) {
		MemberForm loginMember = (MemberForm) session.getAttribute("MEMBER");
		Member member = memberService.findMember(loginMember);
		playlist.setMember(member);
		playlistService.addPlaylist(playlist);
		return "redirect:/mylist";
	}


	@GetMapping("/mylist")
	public String playlist(Model model, HttpSession session, @PageableDefault(size=5, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		MemberForm loginMember = (MemberForm) session.getAttribute("MEMBER");
		if (loginMember!=null) {
			Member member = memberService.findMember(loginMember);
			model.addAttribute("playlists", playlistService.findMylist(pageable, member));
			return "playlist/mylist";
		}
		model.addAttribute("memberForm", new MemberForm());
		return "member/login";
	}
	
	@GetMapping("/mylist/{id}")
	public String detail() {
		return "playlist/detail";
	}
	
	
	@GetMapping("/mylist/search")
	public String search(Model model, String keyword, @PageableDefault(size=5, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("playlists",playlistService.search(pageable, keyword));
		return "playlist/search";
	}
}
