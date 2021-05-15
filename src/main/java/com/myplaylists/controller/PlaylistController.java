package com.myplaylists.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myplaylists.config.auth.dto.SessionUser;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.domain.User;
import com.myplaylists.dto.YoutubeForm;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.SongService;
import com.myplaylists.service.UserService;
import com.myplaylists.service.YoutubeService;

@Controller
public class PlaylistController {
	
	private final PlaylistService playlistService;
	private final UserService userService;
	private final YoutubeService youtubeService;
	private final SongService songService;
	private final HttpSession session;
	
	
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
			model.addAttribute("playlists", playlistService.findMyPlaylists(pageable, userService.findUser(user)));
			
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			
			
			return "playlist/mylist";
		}
		return "index";
	}
	
	@GetMapping("/mylist/search")
	public String search(Model model, String keyword, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("playlists",playlistService.search(pageable, keyword));
		return "playlist/searchPlaylist";
	}
	
	@GetMapping("/mylist/add")
	public String add(Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("playlist", new Playlist());
			return "playlist/addPlaylist";
		}
		return "index";
		
	}
	
	@PostMapping("/mylist/add")
	public String add(@Valid Playlist playlist, BindingResult result) {
		if (result.hasErrors()) {
			return "playlist/addPlaylist";
		}
		SessionUser user = (SessionUser) session.getAttribute("user");
		
		playlist.setMember(userService.findUser(user));
		playlistService.addPlaylist(playlist);
		return "redirect:/mylist";
	}


	@GetMapping("/mylist/{playlistId}")
	public String detail(@PathVariable("playlistId") Long playlistId, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			
			
			Playlist playlist = playlistService.getPlaylist(playlistId);
			model.addAttribute("playlist",playlist);
			model.addAttribute("songs", songService.findSongs(playlist));
			
			return "playlist/detail";
		}
		return "user/login";
	}
	
	@DeleteMapping("/mylist/{playlistId}")
	public String deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		Playlist playlist = playlistService.getPlaylist(playlistId);
		playlistService.deletePlaylist(playlist);
		return "redirect:/mylist";
	}
	
	
	@GetMapping("/mylist/{playlistId}/add")
	public String addsong(@PathVariable("playlistId") Long id, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			
			/*
			 * 해당 플레이리스트 객체화 & 해당 플레이리스트의 노래 페이징 처리
			 */
			Playlist playlist = playlistService.getPlaylist(id);
			model.addAttribute("playlist",playlist);
			model.addAttribute("songs", songService.findSongs(playlist));


			return "playlist/addSong";
		}
		return "index";
	}
	
	
	@GetMapping("/mylist/{playlistId}/youtube_search")
	public String youtubeSearch(@PathVariable("playlistId") Long playlistId, String search, Model model) throws IOException, ParseException {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			/*
			 * 해당 플레이리스트 객체화 & 해당 플레이리스트의 노래 페이징 처리
			 */
			Playlist playlist = playlistService.getPlaylist(playlistId);
			model.addAttribute("playlist",playlist);
			model.addAttribute("songs", songService.findSongs(playlist));
	
			
			/*
			 * 유튜브검색서비스
			 */
			List<YoutubeForm> result = youtubeService.search(search);
			model.addAttribute("result", result);
			
			return "playlist/addSong";
		}
		return "index";
	}
	
	@PostMapping("/mylist/{playlistId}/{videoTitle}/{videoId}")
	public String addSong(@PathVariable("playlistId") Long playlistId, @PathVariable("videoTitle") String videoTitle, @PathVariable("videoId") String videoId) {
		Playlist playlist = playlistService.getPlaylist(playlistId);
		Song song = new Song();
		song.setTitle(videoTitle);
		song.setVideoId(videoId);
		playlist.addSong(song);
		songService.saveSong(song);
		return "redirect:/mylist/{playlistId}";
	}
	
	
	
	@GetMapping("/mylist/{playlistId}/{songId}")
	public String playSong(@PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user!=null) {
			
			/*
			 * 해당 플레이리스트 객체화 & 해당 플레이리스트의 노래 페이징 처리
			 */
			Playlist playlist = playlistService.getPlaylist(playlistId);
			Song song = songService.getSong(songId);
			model.addAttribute("playlist",playlist);
			model.addAttribute("nowSong",song);
			model.addAttribute("songs", songService.findSongs(playlist));


			return "playlist/playSong";
		}
		return "index";
	}
	
	@GetMapping("/mylist/{playlistId}/update/{songId}")
	public String updateSong(Model model, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if (user != null) {
			/*
			 * 해당 플레이리스트 객체화 & 해당 플레이리스트의 노래 페이징 처리
			 */
			Playlist playlist = playlistService.getPlaylist(playlistId);
			Song song = songService.getSong(songId);
			model.addAttribute("playlist",playlist);
			model.addAttribute("nowSong",song);
			model.addAttribute("songs", songService.findSongs(playlist));
			
			return "playlist/updateSong";
		}
		return "index";
	}
	
	@PutMapping("/mylist/{playlistId}/update/{songId}")
	public String updateSong(String title, @PathVariable("songId") Long songId) {
		Song song = songService.getSong(songId);
		song.update(title);
		songService.saveSong(song);
		return "redirect:/mylist/{playlistId}/{songId}";
	}
	
	
	
	@DeleteMapping("/mylist/{playlistId}/{songId}")
	public String deleteSong(@PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
		Song song = songService.getSong(songId);
		songService.deleteSong(song);
		return "redirect:/mylist/{playlistId}";
	}
}
