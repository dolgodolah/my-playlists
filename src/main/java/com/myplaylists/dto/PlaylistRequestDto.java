package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import lombok.Getter;


@Getter
public class PlaylistRequestDto {
    private String title;
    private String description;
    private boolean visibility;

    public Playlist toEntity(User user) {
        return Playlist.builder()
                .user(user)
                .title(title)
                .description(description)
                .visibility(visibility)
                .build();
    }

}
