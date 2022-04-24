package com.myplaylists.repository;


import com.myplaylists.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Playlist;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	Page<Playlist> findByUser(Pageable pageable, User user);
	Page<Playlist> findByTitleContainingAndUser(Pageable pageable, String title, User user);
	Page<Playlist> findByVisibility(Pageable pageable, boolean visibility);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
}
