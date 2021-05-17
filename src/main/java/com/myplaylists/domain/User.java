package com.myplaylists.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.myplaylists.config.auth.OAuthAttributes.OAuthAttributesBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String nickname;
	
//	@Column(nullable=false)
//	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자와 특수문자를 포함한 숫자 8자 이상 입력해주세요.")
//	private String password;
	
	@Column(nullable=false)
	private String email;

	
	/*
	 * 이 관계의 주인은 Playlist에 있고, Playlist에서 user라는 변수로 참조하고 있다.
	 */
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Playlist> playlists = new ArrayList<>();


	@Builder
	public User(String name, String email, String nickname) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
	}
	
	public User update(String name) {
		this.name=name;
		return this;
	}
	
	
}
