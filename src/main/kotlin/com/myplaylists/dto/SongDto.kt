package com.myplaylists.dto

import com.myplaylists.domain.Song
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
    private val videoId: String,
) {
    fun toEntity(): Song = Song(title, videoId)
}

class SongResponseDto(
    val songId: Long,
    val title: String,
    val videoId: String,
    val description: String?,
    val createdDate: String,
) {
    companion object {
        fun of(song: Song) = SongResponseDto(
            songId = song.id,
            title = song.title,
            videoId = song.videoId,
            description = song.description,
            createdDate = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(song.createdDate))
    }
}

class SongsDto(val songs: List<SongResponseDto>): BaseResponse() {
    companion object {
        fun of(songs: List<Song>) = SongsDto(
            songs = songs.stream()
                .map(SongResponseDto::of)
                .collect(Collectors.toList())
        )
    }
}