package com.myplaylists.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.domain.User;
import com.myplaylists.repository.PlaylistRepository;
import com.myplaylists.repository.SongRepository;

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
	public Page<Playlist> findMyPlaylists(Pageable pageable, User user) {
		return playlistRepository.findByUserId(pageable, user.getId());
		
	}
	
	@Transactional
	public void deletePlaylist(Playlist playlist) {
		playlistRepository.deleteById(playlist.getId());
	}
	
	public Page<Playlist> search(Pageable pageable, String keyword){
		return playlistRepository.findByTitleContaining(pageable, keyword);
	}
	
	public Playlist getPlaylist(Long id) {
		return playlistRepository.findById(id).get();
	}
	


}
