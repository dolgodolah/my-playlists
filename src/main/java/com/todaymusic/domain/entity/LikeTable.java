package com.todaymusic.domain.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //파라미터가 없는 생성자를 자동으로 생성
public class LikeTable {
	
	@EmbeddedId
	private LikeKey likeKey;

	@Builder
	public LikeTable(LikeKey likeKey) {
		this.likeKey = likeKey;
	}
	
	
	
	
	

//	@ManyToOne
//	@JoinColumn(name="music_id")
//	private Music music;
	

	
}
