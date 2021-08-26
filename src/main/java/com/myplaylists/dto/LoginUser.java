package com.myplaylists.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.myplaylists.domain.User;

@Getter
public class LoginUser implements Serializable {
	private Long id;
	private String name;
	private String email;
	private String nickname;

    public LoginUser(User user) {
    	this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}