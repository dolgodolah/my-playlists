package com.todaymusic.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String main(Model model) throws ParseException, IOException {
		List<String> videoList = new ArrayList<String>();
		HashMap<String, String> items = weatherService.getItemsFromApi();
		List<MusicDTO> musicList = musicService.getMusicList("1");//items.get("PTY")
		for(int i=0; i<musicList.size(); i++) {
			videoList.add("http://www.youtube.com/embed/"+youtubeService.getVideoId(musicList.get(i).getTitle()+musicList.get(i).getArtist())+"?enablejsapi=1&origin=http://example.com");
		}
		System.out.println(videoList.get(0));
		
//		System.out.println(items);
		model.addAttribute("pty", items.get("PTY"));
		model.addAttribute("t1h", items.get("T1H"));
		model.addAttribute("musicList",videoList);
		
		return "board/list";
	}
	
	@GetMapping("/post")
	public String post() {
		return "board/post";
	}
	
	@PostMapping("/post")
	public String save(MusicDTO musicDTO) {
		musicService.saveMusic(musicDTO);
		return "redirect:/";
	}
}
