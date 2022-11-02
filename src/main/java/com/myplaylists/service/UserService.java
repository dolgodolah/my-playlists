package com.myplaylists.service;

import com.myplaylists.dto.UserDto;
import com.myplaylists.exception.BadRequestException;
import com.myplaylists.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.User;
import com.myplaylists.repository.UserRepository;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	@Cacheable(key = "#userId", value = "user")
	public UserDto findUserById(Long userId) {
		User user = findUserByIdOrElseThrow(userId);
		return user.toDTO();
	}

	@CachePut(key = "#userId", value = "user")
	@CacheEvict(key = "#userId", value = "playlist")
	public UserDto updateUserInfo(Long userId, UserDto userDto) {
		String nickname = userDto.getNickname();
		if (!StringUtils.hasText(nickname)) {
			throw new BadRequestException("닉네임이 공백이거나 입력되지 않았습니다.");
		}

		User user = findUserByIdOrElseThrow(userId).updateNickname(nickname);
		return user.toDTO();
	}

	@Transactional(readOnly = true)
	public User findUserByIdOrElseThrow(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
	}
}
