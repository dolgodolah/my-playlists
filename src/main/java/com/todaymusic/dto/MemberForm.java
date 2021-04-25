package com.todaymusic.dto;

import lombok.Setter;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
@Setter
public class MemberForm {

	@NotBlank(message="유저명을 입력해주세요.")
	private String username;
	
	@NotBlank(message="비밀번호를 입력해주세요.")
	private String password;
}
