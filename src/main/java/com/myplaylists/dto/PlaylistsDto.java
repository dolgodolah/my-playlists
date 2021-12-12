package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PlaylistsDto {
    private List<PlaylistDto> playlists;
    private boolean isLast;

    public static PlaylistsDto of(Page<Playlist> playlists) {
        List<PlaylistDto> playlistDtoList = playlists.stream()
                .map(PlaylistDto::of)
                .collect(Collectors.toList());

        return PlaylistsDto.builder()
                .playlists(playlistDtoList)
                .isLast(playlists.isLast())
                .build();
    }
}
