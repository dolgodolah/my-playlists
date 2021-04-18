package com.todaymusic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PlaylistController {
	
	@GetMapping("/mylist")
	public ModelAndView playlist() {
		ModelAndView mav = new ModelAndView("/playlist/mylist");
		return mav;
	}

}
