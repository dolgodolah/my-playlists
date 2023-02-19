package com.myplaylists.repository;

import com.myplaylists.domain.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	List<Playlist> findAllByUserId(Long userId);
	List<Playlist> findAllByUserIdAndTitleContaining(Long userId, String title);
	Page<Playlist> findByVisibility(Pageable pageable, boolean visibility);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
	Page<Playlist> findByVisibilityAndTitleContaining(Pageable pageable, boolean visibility, String title);
}
