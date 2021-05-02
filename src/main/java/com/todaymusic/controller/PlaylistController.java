package com.todaymusic.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.todaymusic.domain.User;
import com.todaymusic.config.auth.dto.SessionUser;
import com.todaymusic.domain.Playlist;
import com.todaymusic.domain.Song;
import com.todaymusic.dto.YoutubeForm;
import com.todaymusic.service.UserService;
import com.todaymusic.service.PlaylistService;
import com.todaymusic.service.SongService;
import com.todaymusic.service.YoutubeService;

@Controller
public class PlaylistController {
	
	PlaylistService playlistService;
	UserService userService;
	YoutubeService youtubeService;
	SongService songService;
	HttpSession session;
	
	@Autowired
	public PlaylistController(PlaylistService playlistService, UserService userService, YoutubeService youtubeService, SongService songService, HttpSession session) {
		this.playlistService = playlistService;
		this.userService = userService;
		this.youtubeService = youtubeService;
		this.songService = songService;
		this.session = session;
	}
	
	@GetMapping("/mylist")
	public String playlist(Model model, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("playlists", playlistService.findMylist(pageable, userService.findUser(user)));
			
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			return "playlist/mylist";
		}
		return "index";
	}
	
	@GetMapping("/mylist/add")
	public String add(Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("playlist", new Playlist());
			return "playlist/addlist";
		}
		return "index";
		
	}
	
	@Transactional
	@PostMapping("/mylist/add")
	public String add(@Valid Playlist playlist, BindingResult result) {
		if (result.hasErrors()) {
			return "playlist/addlist";
		}
		SessionUser user = (SessionUser) session.getAttribute("user");
		
		playlist.setMember(userService.findUser(user));
		playlistService.addPlaylist(playlist);
		return "redirect:/mylist";
	}


	
	
	@GetMapping("/mylist/{id}")
	public String detail(@PathVariable("id") Long id, Model model, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("playlists", playlistService.findMylist(pageable, userService.findUser(user)));
			
			Playlist playlist = playlistService.getPlaylist(id);
			model.addAttribute("playlist",playlist);
			
			

			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			return "playlist/detail";
		}
		return "user/login";
	}
	
	
	@GetMapping("/mylist/search")
	public String search(Model model, String keyword, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("playlists",playlistService.search(pageable, keyword));
		return "playlist/search";
	}
	
	
	@GetMapping("/mylist/{id}/add")
	public String addsong(@PathVariable("id") Long id, Model model, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			
			/*
			 * 페이징 처리
			 */
			model.addAttribute("playlists", playlistService.findMylist(pageable, userService.findUser(user)));
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			
			/*
			 * 해당 플레이리스트 객체 생성
			 */
			Playlist playlist = playlistService.getPlaylist(id);
			model.addAttribute("playlist",playlist);

			return "playlist/addsong";
		}
		return "index";
	}
	
	@GetMapping("/mylist/{id}/youtube_search")
	public String youtubeSearch(@PathVariable("id") Long id, String search, Model model, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) throws IOException, ParseException {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			/*
			 * 페이징 처리
			 */
			model.addAttribute("playlists", playlistService.findMylist(pageable, userService.findUser(user)));
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			
			/*
			 * 해당 플레이리스트 객체 생성
			 */
			Playlist playlist = playlistService.getPlaylist(id);
			model.addAttribute("playlist",playlist);
			
			/*
			 * 유튜브검색서비스
			 */
			List<YoutubeForm> result = youtubeService.search(search);
			model.addAttribute("result", result);
			
			return "playlist/addsong";
		}
		return "index";
	}
	
	@Transactional
	@PostMapping("/mylist/{id}/add/{videoId}")
	public String addSong(@PathVariable("id") Long id, @PathVariable("videoId") String videoId, Model model, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		Playlist playlist = playlistService.getPlaylist(id);
		Song song = new Song();
		song.setUrl(videoId);
		playlist.addSong(song);
		songService.addMusic(song);
		return "redirect:/mylist/{id}";
	}
}
