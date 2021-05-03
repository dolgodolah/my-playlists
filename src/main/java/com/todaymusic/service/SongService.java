package com.todaymusic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Playlist;
import com.todaymusic.domain.Song;
import com.todaymusic.repository.SongRepository;

@Service
public class SongService {
	
	private final SongRepository songRepository;

	@Autowired
	public SongService(SongRepository songRepository) {
		this.songRepository = songRepository;
	}
	
	@Transactional
	public Long addSong(Song song) {
		return songRepository.save(song).getId();
	}
	
	public Page<Song> findSongs(Pageable pageable, Playlist playlist){
		return songRepository.findByPlaylistId(pageable, playlist.getId());
	}
	
	public Song getSong(Long id) {
		return songRepository.findById(id).get();
	}
	
}
