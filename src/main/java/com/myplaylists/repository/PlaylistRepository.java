package com.myplaylists.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	Page<Playlist> findByUserId(Pageable pageable, Long userId);
	Page<Playlist> findByTitleContainingAndUserId(Pageable pageable, String title, Long userId);
	Page<Playlist> findByVisibility(Pageable pageable, boolean visibility);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
}
