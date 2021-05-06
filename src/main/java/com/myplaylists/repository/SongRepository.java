package com.myplaylists.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
//	Page<Song> findByPlaylistId(Pageable pageable, Long playlistId);
	List<Song> findByPlaylistIdOrderByCreatedAtDesc(Long playlistId);
}
