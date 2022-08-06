package com.myplaylists.service;


import com.myplaylists.dto.UserDto;
import com.myplaylists.exception.ApiException;
import com.myplaylists.exception.InvalidNicknameException;
import com.myplaylists.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.User;
import com.myplaylists.repository.UserRepository;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserDto findUserById(Long userId) {
		return UserDto.of(findUserByIdOrElseThrow(userId));
	}

	@Transactional
	public UserDto updateUserInfo(Long userId, UserDto userDto) {
		if (!StringUtils.hasText(userDto.getNickname())) {
			throw new InvalidNicknameException();
		}

		User user = findUserByIdOrElseThrow(userId);
		user.updateNickname(userDto.getNickname());
		
		return UserDto.of(user);
	}

	public User findUserByIdOrElseThrow(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
	}
}
