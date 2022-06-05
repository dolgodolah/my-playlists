package com.myplaylists.service;


import com.myplaylists.dto.UserDto;
import com.myplaylists.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.User;
import com.myplaylists.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User findByUserId(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않는 사용자입니다."));
	}

	@Transactional
	public UserDto updateUserInfo(Long userId, UserDto userDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않는 사용자입니다."));

		if (userDto.getNickname().isEmpty()) {
			throw new ApiException("닉네임을 입력하지 않았습니다.", 1);
		}

		user.updateNickname(userDto.getNickname());
		return UserDto.of(user);
	}
}
