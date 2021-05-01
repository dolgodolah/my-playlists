package com.todaymusic.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Member;
import com.todaymusic.domain.Playlist;
import com.todaymusic.domain.Song;
import com.todaymusic.repository.PlaylistRepository;
import com.todaymusic.repository.SongRepository;

@Service
public class PlaylistService {
	
	private PlaylistRepository playlistRepository;
	private SongRepository songRepository;
	
	
	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository) {
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
	}


	@Transactional
	public Long addPlaylist(Playlist playlist) {
		return playlistRepository.save(playlist).getId();	}
	
	@Transactional
	public Page<Playlist> findMylist(Pageable pageable, Member member) {
		return playlistRepository.findByMemberId(pageable, member.getId());
		
	}
	
	public Page<Playlist> search(Pageable pageable, String keyword){
		return playlistRepository.findByTitleContaining(pageable, keyword);
	}
	
	public Playlist getPlaylist(Long id) {
		return playlistRepository.findById(id).get();
	}
	


}
