package com.myplaylists.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.config.auth.dto.SessionUser;
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
	
	public User findUser(SessionUser sessionUser) {
		User user = userRepository.findByEmail(sessionUser.getEmail()).get();
		return user;
	}
	
	public Long updateUser(SessionUser sessionUser,String nickname) {
		User user = userRepository.findByEmail(sessionUser.getEmail()).get();
		user.setNickname(nickname);
		userRepository.save(user);
		return user.getId();
	}
	
	public String getAuthor(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			String nickname = user.get().getNickname();
			return (nickname != null) ? nickname : user.get().getName();
		}
		return "알수없음";
	}
}
