package com.myplaylists.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongRequestDto {
    private String playlistId;
    private String title;
    private String videoId;
    private String description;
}
