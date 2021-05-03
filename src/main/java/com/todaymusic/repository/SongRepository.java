package com.todaymusic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	Page<Song> findByPlaylistId(Pageable pageable, Long playlistId);

}
