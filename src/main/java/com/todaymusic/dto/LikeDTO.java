package com.todaymusic.dto;

import com.todaymusic.domain.entity.LikeKey;
import com.todaymusic.domain.entity.LikeTable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeDTO {
	
	private LikeKey likeKey;
	
	public LikeTable toEntity() {
		LikeTable build = LikeTable.builder()
				.likeKey(likeKey)
				.build();
		return build;
	}

	@Builder
	public LikeDTO(LikeKey likeKey) {
		this.likeKey = likeKey;
	}
	
}
