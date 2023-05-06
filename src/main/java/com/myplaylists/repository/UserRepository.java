package com.myplaylists.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllByEmail(String email);
}
