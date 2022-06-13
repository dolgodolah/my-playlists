package com.myplaylists.dto

import com.myplaylists.domain.Song
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

class SongAddRequestDto(
    val playlistId: Long,
    val title: String,
    private val videoId: String,
) {
    fun toEntity(): Song = Song(title, videoId)
}

class SongUpdateRequestDto(
    val title: String,
    val description: String,
) {
}

class SongResponseDto(
    val songId: Long,
    val title: String,
    val videoId: String,
    val description: String?,
    val createdDate: LocalDateTime,
) {
    companion object {
        fun of(song: Song) = SongResponseDto(
            songId = song.id,
            title = song.title,
            videoId = song.videoId,
            description = song.description,
            createdDate = song.createdDate)
    }
}

class SongsDto(val songs: List<SongResponseDto>): BaseResponse() {
    companion object {
        fun of(songs: List<Song>) = SongsDto(
            songs = songs.stream()
                .map(SongResponseDto::of)
                .sorted(Comparator.comparing(SongResponseDto::createdDate).reversed())
                .collect(Collectors.toList())
        )
    }
}