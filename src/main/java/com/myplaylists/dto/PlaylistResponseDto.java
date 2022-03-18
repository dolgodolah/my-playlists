package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PlaylistResponseDto {
    private Long playlistId;
    private String title;
    private String description;
    private LocalDateTime updatedDate;
    private boolean visibility;
    private String author;
    private boolean isBookmark;
    private int songCount;

    public static PlaylistResponseDto of(Playlist playlist) {
        return PlaylistResponseDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .author(playlist.getUser().getNickname())
                .songCount(playlist.getSongCount())
                .build();
    }

    public static PlaylistResponseDto of(Playlist playlist, boolean isBookmark) {
        return PlaylistResponseDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .author(playlist.getUser().getNickname())
                .songCount(playlist.getSongCount())
                .isBookmark(isBookmark)
                .build();
    }
}
