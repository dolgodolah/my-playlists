package com.myplaylists.repository;

import java.util.List;

import com.myplaylists.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Song;
import org.springframework.data.jpa.repository.Query;

public interface SongRepository extends JpaRepository<Song, Long>{
	List<Song> findByPlaylistAndTitleContaining(Playlist playlist, String keyword);
	@Query("SELECT s FROM Song s WHERE s.playlist.id = ?1")
	List<Song> findAllByPlaylistId(Long playlistId);
}
