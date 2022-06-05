package com.myplaylists.dto;

import com.myplaylists.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongResponseDto {
    private Long songId;
    private String title;
    private String videoId;
    private String description;
    private String createdDate;

    public static SongResponseDto of(Song song) {
        return SongResponseDto.builder()
                .songId(song.getId())
                .title(song.getTitle())
                .videoId(song.getVideoId())
                .description(song.getDescription())
                .createdDate(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(song.getCreatedDate()))
                .build();
    }
}
