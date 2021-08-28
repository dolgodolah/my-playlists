package com.myplaylists.dto;

import com.myplaylists.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongResponseDto {
    private String title;
    private String videoId;
    private String description;

    public static SongResponseDto of(Song song) {
        return SongResponseDto.builder()
                .title(song.getTitle())
                .videoId(song.getVideoId())
                .description(song.getDescription())
                .build();
    }
}
