package com.todaymusic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Song {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="song_id")
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="playlist_id")
	private Playlist playlist;

	
	
}
