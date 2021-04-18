package com.todaymusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	List<Playlist> findByMemberId(Long memberId);
}
