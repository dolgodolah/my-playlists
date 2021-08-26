package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
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
