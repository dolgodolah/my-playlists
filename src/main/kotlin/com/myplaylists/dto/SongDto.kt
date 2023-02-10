package com.myplaylists.dto

import com.fasterxml.jackson.annotation.JsonProperty

class SongAddRequestDto(
    @JsonProperty("p")
    val encryptedId: String,
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
    val createdDate: String,
    val updatedDate: String,
) {
}

class SongsDto(
    val songs: List<SongResponseDto>
): BaseResponse() {
}