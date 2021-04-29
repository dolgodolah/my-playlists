package com.todaymusic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	Page<Playlist> findByMemberId(Pageable pageable, Long memberId);
	Page<Playlist> findByTitleContaining(Pageable pageable, String title);
}
