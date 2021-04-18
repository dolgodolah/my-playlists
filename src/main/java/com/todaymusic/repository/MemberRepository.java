package com.todaymusic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaymusic.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
}
