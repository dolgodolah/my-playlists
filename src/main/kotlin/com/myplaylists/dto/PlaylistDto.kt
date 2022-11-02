package com.myplaylists.dto

import java.time.LocalDateTime
import java.util.Comparator

class PlaylistRequestDto(
    val title: String,
    val description: String,
    val visibility: Boolean,
) {
}

class PlaylistResponseDto(
    val playlistId: Long,
    val title: String,
    val description: String,
    val updatedDate: LocalDateTime,
    val visibility: Boolean,
    val author: String,
    val isBookmark: Boolean,
    val songCount: Int
): BaseResponse() {
}

class PlaylistsDto(
    val playlists: List<PlaylistResponseDto>,
): BaseResponse() {
    companion object {
        fun of(playlists: List<PlaylistResponseDto>): PlaylistsDto {
            val playlistDtoList = playlists.sortedWith(Comparator.comparing(PlaylistResponseDto::updatedDate).reversed())

            return PlaylistsDto(
                playlists = playlistDtoList
            )
        }
    }
}