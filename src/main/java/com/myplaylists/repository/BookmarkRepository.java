package com.myplaylists.repository;

import java.util.Optional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Bookmark;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	Page<Bookmark> findByUserId(Pageable pageable, Long userId);
	@Query(value = "SELECT b FROM Bookmark b WHERE b.user.id = ?1 AND b.playlist.id = ?2")
	Optional<Bookmark> findByUserIdAndPlaylistId(Long userId, Long playlistId);
}
