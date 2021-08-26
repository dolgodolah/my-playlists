package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDto {
    private String title;
    private String description;
    private boolean visibility;

    public static PlaylistResponseDto of(Playlist playlist) {
        return PlaylistResponseDto.builder()
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .visibility(playlist.isVisibility())
                .build();
    }
}
