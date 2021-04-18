package com.todaymusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	List<Song> findByPlaylistId(Long playlistId);

}
