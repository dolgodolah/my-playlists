package com.todaymusic.dto;

import com.todaymusic.domain.entity.Music;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MusicDTO {
	private Long id;
	private String title;
	private String artist;
	private String pty;
	private String t1h;
	
	public Music toEntity() {
		Music build = Music.builder()
				.id(id)
				.title(title)
				.artist(artist)
				.pty(pty)
				.t1h(t1h)
				.build();
		return build;
	}
	
	@Builder
	public MusicDTO(Long id, String title, String artist, String pty, String t1h) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.pty = pty;
		this.t1h = t1h;
	}
	
	
	
}
