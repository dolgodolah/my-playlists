package com.myplaylists.dto

import java.time.LocalDateTime

class SongAddRequestDto(
    val playlistId: Long,
    val title: String,
    val videoId: String,
) {
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
    val updatedDate: LocalDateTime,
) {
}

class SongsDto(
    val songs: List<SongResponseDto>
): BaseResponse() {
}