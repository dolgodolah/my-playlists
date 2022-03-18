package com.myplaylists.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.myplaylists.domain.User;

@Getter
public class LoginUser implements Serializable {
	private Long userId;

    public LoginUser(User user) {
    	this.userId = user.getUserId();
    }
}