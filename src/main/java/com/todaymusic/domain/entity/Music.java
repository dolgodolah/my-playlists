package com.todaymusic.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String title;
	
	@Column(length = 20, nullable = false)
	private String artist;
	
	@Column(length = 1, nullable = false)
	private String pty;
	
	@Column
	private String t1h;
	
	@Column(columnDefinition = "bigint default 0", nullable = false)
	private Long likeCount;
	
	@Builder
	public Music(Long id, String title, String artist, String pty, String t1h, Long likeCount) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.pty = pty;
		this.t1h = t1h;
		this.likeCount = likeCount;
	}
	
}
