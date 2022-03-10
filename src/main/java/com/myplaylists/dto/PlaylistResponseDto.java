package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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
    private List<SongResponseDto> songs;
    private boolean isBookmark;

    public static PlaylistResponseDto of(Playlist playlist) {
        List<SongResponseDto> songs = playlist.getSongs().stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistResponseDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .author(playlist.getUser().getNickname())
                .songs(songs)
                .build();
    }

    public static PlaylistResponseDto of(Playlist playlist, boolean isBookmark) {
        List<SongResponseDto> songs = playlist.getSongs().stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistResponseDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .author(playlist.getUser().getNickname())
                .songs(songs)
                .isBookmark(isBookmark)
                .build();
    }
}
