package com.myplaylists.domain;

import javax.persistence.*;


import lombok.*;


@Entity
@Getter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	private String name;

	private String nickname;

	@Column(nullable=false)
	private String email;

	@Builder
	public User(String name, String email, String nickname) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
	}
	
	public User updateName(String name) {
		this.name = name;
		return this;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
