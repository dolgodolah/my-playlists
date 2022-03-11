package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class SongsDto {
    private List<SongResponseDto> songs;

    public static SongsDto of(Playlist playlist) {
        List<SongResponseDto> songs = playlist.getSongs().stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return SongsDto.builder()
                .songs(songs)
                .build();
    }

    public static SongsDto of(List<Song> songs) {
        List<SongResponseDto> songsDto = songs.stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList());

        return SongsDto.builder()
                .songs(songsDto)
                .build();
    }
}
