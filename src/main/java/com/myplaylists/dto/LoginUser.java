package com.myplaylists.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.myplaylists.domain.User;

@Getter
public class LoginUser implements Serializable {
	private Long id;

    public LoginUser(User user) {
    	this.id = user.getId();
    }
}