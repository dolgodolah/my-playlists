package com.myplaylists.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myplaylists.domain.Song;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>{
	List<Song> findByPlaylistIdAndTitleContaining(Long playlistId, String keyword);
	List<Song> findAllByPlaylistId(Long playlistId);
}
