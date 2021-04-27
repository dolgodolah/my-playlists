package com.todaymusic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Member;
import com.todaymusic.dto.MemberForm;
import com.todaymusic.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}


	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		return memberRepository.save(member).getId();
	}
	
	/*
	 * 동시에 같은 username의 값이 들어오면 validateDuplicateMember 로직을 통과할 수 있다.
	 * 그러니 DB 제약조건인 unique를 통해 중복 회원을 최종적으로 처리해야한다.
	 */
	private void validateDuplicateMember(Member member) {
		if (memberRepository.findByUsername(member.getUsername()).isPresent()) {
			throw new IllegalStateException("이미 존재하는 유저명입니다.");
		}
	}
	
	public boolean login(Member member) {
		Optional<Member> result = memberRepository.findByUsername(member.getUsername());
		if (result.isPresent() && result.get().getPassword().equals(member.getPassword()))
			return true;
		else
			return false;
	}
	
	public Member findMember(MemberForm memberForm) {
		Member member = memberRepository.findByUsername(memberForm.getUsername()).get();
		return member;
	}
	
}
