package com.myplaylists.dto;

import lombok.Getter;

@Getter
public class SongRequestDto {
    private Long playlistId;
    private String title;
    private String videoId;
    private String description;
}
