package com.myplaylists.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaylistsDto {
    private List<PlaylistDto> playlists;
    private boolean isLast;
}
