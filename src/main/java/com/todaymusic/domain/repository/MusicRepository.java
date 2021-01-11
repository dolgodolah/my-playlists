package com.todaymusic.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Long>{

}
