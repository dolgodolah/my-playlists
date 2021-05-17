package com.myplaylists.config.auth.dto;

import lombok.Getter;

import java.io.Serializable;

import com.myplaylists.domain.User;

@Getter
public class SessionUser implements Serializable {
	private Long id;
	private String name;
	private String email;

    public SessionUser(User user) {
    	this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}