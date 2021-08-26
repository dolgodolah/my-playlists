package com.myplaylists.domain;

import javax.persistence.*;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;

	private String name;

	private String nickname;

	@Column(nullable=false)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Bookmark> bookmarks = new ArrayList<>();

	@Builder
	public User(String name, String email, String nickname) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
	}

	public void addBookmark(Bookmark bookmark) {
		this.bookmarks.add(bookmark);
		bookmark.setUser(this);
	}
	
	public User updateName(String name) {
		this.name = name;
		return this;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
