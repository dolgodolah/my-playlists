package com.myplaylists.service;

import com.myplaylists.dto.UserDto;
import com.myplaylists.exception.InvalidNicknameException;
import com.myplaylists.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
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
	public UserDto findUserById(Long userId) {
		return UserDto.of(findUserByIdOrElseThrow(userId));
	}

	public UserDto updateUserInfo(Long userId, UserDto userDto) {
		if (!StringUtils.hasText(userDto.getNickname())) {
			throw new InvalidNicknameException();
		}

		User user = findUserByIdOrElseThrow(userId);
		updateUserInfo(user, userDto);
		return UserDto.of(user);
	}

	@Cacheable(key = "#user.userId", value = "user")
	public User updateUserInfo(User user, UserDto userDto) {
		//TODO: 스프링 캐시는 AOP를 이용하여 Proxy 객체가 생성되기 때문에 내부 메서드 호출에 Cacheable 적용이 안된다.
		//TODO: 해결 방법 1. Service <-> Repository 사이에 프록시 계층 (Cacheable 전용 계층?)을 만든다.
		//TODO: 해결 방법 2. Controller 계층에 Entity 의존을 허용하고 Entity 그대로 반환한다.
		//TODO: 해결 방법 3. self-autowiring 을 통해 해결
		return user.updateNickname(userDto.getNickname());
	}

	@Transactional(readOnly = true)
	@Cacheable(key = "#userId", value = "user")
	public User findUserByIdOrElseThrow(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
	}
}
