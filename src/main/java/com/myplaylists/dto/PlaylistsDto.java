package com.myplaylists.dto;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PlaylistsDto {
    private List<PlaylistResponseDto> playlists;
    private boolean isLast;

    public static PlaylistsDto of(Page<Playlist> playlists) {
        List<PlaylistResponseDto> playlistResponseDtoList = playlists.stream()
                .map(PlaylistResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistsDto.builder()
                .playlists(playlistResponseDtoList)
                .isLast(playlists.isLast())
                .build();
    }

    public static PlaylistsDto of(BookmarksDto bookmarksDto) {
        List<PlaylistResponseDto> playlistResponseDtoList = bookmarksDto.getBookmarks().stream()
                .map(Bookmark::getPlaylist)
                .map(PlaylistResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistsDto.builder()
                .playlists(playlistResponseDtoList)
                .isLast(bookmarksDto.isLast())
                .build();
    }
}
