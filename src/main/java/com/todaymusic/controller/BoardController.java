package com.todaymusic.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.todaymusic.dto.MusicDTO;
import com.todaymusic.service.MusicService;
import com.todaymusic.service.WeatherService;



@Controller
public class BoardController {
	@Autowired
	MusicService musicService;
	@Autowired
	WeatherService weatherService;
	
	
	
	@GetMapping("/")
	public String main(Model model) throws ParseException {
		HashMap<String, String> items = weatherService.getItemsFromApi();
//		System.out.println(items);
		model.addAttribute("pty", items.get("PTY"));
		model.addAttribute("t1h", items.get("T1H"));
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
