package com.todaymusic.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.entity.LikeKey;
import com.todaymusic.domain.entity.LikeTable;

public interface LikeRepository extends JpaRepository<LikeTable, Long>{
	
	//Optional은 null값을 포함해 받을 수 있다.
	Optional<LikeTable> findByLikeKey(LikeKey likeKey);

}
