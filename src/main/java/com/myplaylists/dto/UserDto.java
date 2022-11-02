package com.myplaylists.dto;

import lombok.Getter;

@Getter
public class UserDto extends BaseResponse {
	public String email;
	public String name;
	public String nickname;

	public UserDto(String email, String name, String nickname) {
		this.email = email;
		this.name = name;
		this.nickname = nickname;
	}
}
