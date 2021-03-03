package com.todaymusic.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class LikeKey implements Serializable{
	
	@Column
	Long musicId;
	
	@Column
	String userIp;
}
