package com.myplaylists.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class UserForm {
	private Long id;
	private String name;
	
	@NotBlank(message="닉네임을 입력하지 않았습니다.")
	private String nickname;
	
	private String email;
}
