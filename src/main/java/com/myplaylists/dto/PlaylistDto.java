package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PlaylistDto {
    private Long playlistId;
    private String title;
    private String description;
    private LocalDateTime updatedDate;
    private boolean visibility;
    private String author;
    private List<SongResponseDto> songs;
    private boolean isBookmark;

    public static PlaylistDto of(Playlist playlist) {
        List<SongResponseDto> songs = playlist.getSongs().stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .author(playlist.getUser().getNickname())
                .songs(songs)
                .build();
    }

    public static PlaylistDto of(Playlist playlist, boolean isBookmark) {
        List<SongResponseDto> songs = playlist.getSongs().stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistDto.builder()
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
