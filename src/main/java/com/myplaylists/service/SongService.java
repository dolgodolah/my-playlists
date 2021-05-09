package com.myplaylists.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.repository.SongRepository;

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
	
	@Transactional
	public void deleteSong(Song song) {
		songRepository.deleteById(song.getId());
	}
	
	@Transactional
	public Long updateSong(Song song, String title) {
		song.setTitle(title);
		return songRepository.save(song).getId();
	}
	
//	public Page<Song> findSongs(Pageable pageable, Playlist playlist){
//		return songRepository.findByPlaylistId(pageable, playlist.getId());
//	}
	
	public List<Song> findSongs(Playlist playlist){
		return songRepository.findByPlaylistIdOrderByCreatedAtDesc(playlist.getId());
	}
	
	public Song getSong(Long id) {
		return songRepository.findById(id).get();
	}
	
}
