package com.todaymusic.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.entity.Music;


public interface MusicRepository extends JpaRepository<Music, Long>{
	List<Music> findByPty(String pty);
}
