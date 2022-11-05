package com.myplaylists.service;

import com.myplaylists.dto.UserDto;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.oauth.OauthDto;
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

	/**
	 * 내플리스 회원가입 처리
	 */
	public LoginUser signup(OauthDto oauthDto) {
		if (!StringUtils.hasText(oauthDto.getEmail())) {
			throw new BadRequestException("이메일은 필수입니다. 카카오로 로그인 시 이메일 제공에 동의하지 않았다면 카카오 계정 > 연결된 서비스 관리 > 내플리스 > 연결 끊기 후 이메일 제공에 동의해주세요.");
		}

		User user = userRepository.save(User.Companion.of(oauthDto));
		return new LoginUser(user);
	}

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
