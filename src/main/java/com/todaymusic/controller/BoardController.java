package com.todaymusic.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.todaymusic.dto.MusicDTO;
import com.todaymusic.service.MusicService;
import com.todaymusic.service.WeatherService;
import com.todaymusic.service.YoutubeService;



@Controller
public class BoardController {
//	@Autowired
//	MusicService musicService;
//	@Autowired
//	WeatherService weatherService;
	
	@Autowired
	private MusicService musicService;
	private WeatherService weatherService;
	private YoutubeService youtubeService;
	public BoardController(MusicService musicService, WeatherService weatherService, YoutubeService youtubeService) {
		this.musicService = musicService;
		this.weatherService = weatherService;
		this.youtubeService = youtubeService;
	}
	

	@GetMapping("/")
	public String main(Model model, @PageableDefault(size=10, sort="likeCount", direction=Sort.Direction.DESC) Pageable pageable) throws ParseException{
		HashMap<String, String> items = weatherService.getItemsFromApi(); //공공데이터api로부터 날씨정보 얻어온다.
//		System.out.println(items.get("PTY")+items.get("SKY"));
		model.addAttribute("musicList",musicService.getMusicList(pageable,items.get("PTY"),items.get("SKY"))); //items.get("PTY"),itmes.get("SKY")
		model.addAttribute("previous",pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next",pageable.next().getPageNumber());
		return "board/list";
	}
	
	
	@GetMapping("/post")
	public String post() {
		return "board/post";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) throws IOException, ParseException {
		MusicDTO musicDTO = musicService.getMusic(id);
		model.addAttribute("music", musicDTO);
		String videoURL;
		videoURL = "http://www.youtube.com/embed/"+youtubeService.getVideoId(musicDTO.getArtist()+musicDTO.getTitle())+"?enablejsapi=1&origin=http://example.com";
		model.addAttribute("videoURL",videoURL);
		return "board/detail";
	}
	
	@PostMapping("/detail/{id}")
	public String recommend(@PathVariable("id") Long id, Model model) {
		musicService.setLikeCount(id);
//		model.addAttribute("check", check);
		return "redirect:/detail/{id}";
	}
	
	@PostMapping("/post")
	public String save(MusicDTO musicDTO) {
		musicService.saveMusic(musicDTO);
		return "redirect:/";
	}
}
