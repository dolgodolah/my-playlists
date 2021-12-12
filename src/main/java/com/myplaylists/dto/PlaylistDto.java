package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PlaylistDto {
    private Long playlistId;
    private String title;
    private String description;
    private LocalDateTime updatedDate;
    private boolean visibility;

    public static PlaylistDto of(Playlist playlist) {
        return PlaylistDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .build();
    }
}
