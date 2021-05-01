package com.todaymusic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YoutubeForm {
	private String videoId;
	private String title;
	private String thumbnail;
	@Override
	public String toString() {
		return "YoutubeForm [videoId=" + videoId + ", title=" + title + ", thumbnail=" + thumbnail + "]";
	}
	
	
}
