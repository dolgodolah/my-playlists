package com.todaymusic.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.todaymusic.domain.Member;
import com.todaymusic.repository.MemberRepository;
import com.todaymusic.service.MemberService;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	
	/*
	 * given, when, then이 Test의 기본 구조이다.
	 */
	@Test
	public void 회원가입() throws Exception{
		//given
		Member member = new Member();
		member.setUsername("dolgodolah");
		member.setPassword("1234");
		//when
		Long savedId = memberService.join(member);
		//then
		//member와 repository에서 꺼내온 member값이 같다고 하는 이유는
		//동일한 트랜잭션에서 수행되기 때문이다. 즉, 동일한 영속성 context에서 관리되기 때문에 아래 assert는 true가 나온다.
		assertEquals(member, memberRepository.findById(savedId).get());
		
		
	}
	
	@Test
	public void 중복_회원_예외() throws Exception{
		
	}

}
