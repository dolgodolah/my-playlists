package com.todaymusic.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
//	이 관계의 주인은 Playlist에 있고, Playlist에서 member라는 변수로 참조하고 있다.
	@OneToMany(mappedBy="member")
	private List<Playlist> playlists = new ArrayList<>();

	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

	
	
}
