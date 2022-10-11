package com.myplaylists.repository;

import java.util.List;

import com.myplaylists.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	List<Song> findAllByPlaylistOrderByIdDesc(Playlist playlist);
	List<Song> findSongsByPlaylist(Playlist playlist);
	List<Song> findByPlaylistAndTitleContaining(Playlist playlist, String keyword);
	List<Song> findByPlaylistId(Long playlistId);
}
