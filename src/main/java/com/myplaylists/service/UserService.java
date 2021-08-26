package com.myplaylists.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.domain.User;
import com.myplaylists.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않는 사용자입니다."));
	}

	public User findUser(LoginUser loginUser) {
		User user = userRepository.findByEmail(loginUser.getEmail()).get();
		return user;
	}
	
	public void updateUserInfo(Long userId, String nickname) {
		User user = getUser(userId);
		user.updateNickname(nickname);
	}
}
