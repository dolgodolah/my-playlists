package com.todaymusic.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todaymusic.domain.repository.MusicRepository;
import com.todaymusic.dto.MusicDTO;

@Service
public class MusicService {
	private MusicRepository musicRepository;

	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}
	
	@Transactional
	public Long saveMusic(MusicDTO musicDTO) {
		return musicRepository.save(musicDTO.toEntity()).getId();
	}
	
}
