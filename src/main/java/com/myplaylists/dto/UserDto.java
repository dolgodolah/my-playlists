package com.myplaylists.dto;

import com.myplaylists.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseResponse {
	private String email;
	private String name;
	private String nickname;

	public static UserDto of(User user) {
		return UserDto.builder()
				.email(user.getEmail())
				.name(user.getName())
				.nickname(user.getNickname())
				.build();
	}
}
