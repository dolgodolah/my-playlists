package com.myplaylists.repository;


import com.myplaylists.domain.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	@Query(value = "SELECT p FROM Playlist p WHERE p.user.id = ?1")
	List<Playlist> findAllByUserId(Long userId);
	@Query(value = "SELECT p FROM Playlist p WHERE p.user.id = ?1 AND p.title LIKE %?2%")
	List<Playlist> findAllByUserIdAndTitleContaining(Long userId, String title);
	Page<Playlist> findByVisibility(Pageable pageable, boolean visibility);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
	Page<Playlist> findByVisibilityAndTitleContaining(Pageable pageable, boolean visibility, String title);
}
