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
	public Long userId;

	public String name;

	public String nickname;

	@Column(nullable=false)
	public String email;

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
