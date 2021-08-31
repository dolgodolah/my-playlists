package com.myplaylists.repository;


import com.myplaylists.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Playlist;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	List<Playlist> findAllByUser(Pageable pageable, User user);
	long countAllByUser(User user);
	Page<Playlist> findByUserId(Pageable pageable, Long userId);
	Page<Playlist> findByTitleContainingAndUserId(Pageable pageable, String title, Long userId);
	List<Playlist> findByVisibility(Pageable pageable, boolean visibility);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
}
