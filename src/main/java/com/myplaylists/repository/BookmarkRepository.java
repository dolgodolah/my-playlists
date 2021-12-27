package com.myplaylists.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
	Page<Bookmark> findByUserId(Pageable pageable, Long userId);
	Optional<Bookmark> findByUserIdAndPlaylistId(Long UserId, Long PlaylistId);
	Optional<Bookmark> findAllByUserId(Long userId);

}
