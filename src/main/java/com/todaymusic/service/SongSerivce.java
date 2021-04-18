package com.todaymusic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Song;
import com.todaymusic.repository.SongRepository;

@Service
public class SongSerivce {
	
	private SongRepository musicRepository;

	@Autowired
	public SongSerivce(SongRepository musicRepository) {
		this.musicRepository = musicRepository;
	}
	
	@Transactional
	public Long addMusic(Song music) {
		return musicRepository.save(music).getId();
	}
	
}
