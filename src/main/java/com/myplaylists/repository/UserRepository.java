package com.myplaylists.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByName(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByNickname(String nickname);
}
