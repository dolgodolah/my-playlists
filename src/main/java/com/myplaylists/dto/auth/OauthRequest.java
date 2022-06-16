package com.myplaylists.dto.auth;

import com.myplaylists.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OauthRequest {
    private String email;
    private String name;
    private Map<String, Object> attributes;

    @Builder
    public OauthRequest(String email, String name, Map<String, Object> attributes) {
        this.email = email;
        this.name = name;
        this.attributes = attributes;
    }

    public User toEntity() {
        return new User(null, email, name, name);
    }
}
