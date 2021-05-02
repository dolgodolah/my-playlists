package com.todaymusic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.config.auth.dto.SessionUser;
import com.todaymusic.domain.User;
import com.todaymusic.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findUser(SessionUser sessionUser) {
		User user = userRepository.findByEmail(sessionUser.getEmail()).get();
		return user;
	}
	
}
