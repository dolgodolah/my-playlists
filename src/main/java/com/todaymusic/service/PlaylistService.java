package com.todaymusic.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Member;
import com.todaymusic.domain.Playlist;
import com.todaymusic.repository.PlaylistRepository;

@Service
public class PlaylistService {
	
	private PlaylistRepository playlistRepository;
	
	
	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository) {
		this.playlistRepository = playlistRepository;
	}


	@Transactional
	public Long addPlaylist(Playlist playlist) {
		return playlistRepository.save(playlist).getId();	}
	

	@Transactional
	public List<Playlist> findPlaylist(Member member) {
		return playlistRepository.findByMemberId(member.getId());
	}
	


}
