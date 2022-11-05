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
    val playlistId: Long,               // 식별값
    val title: String,                  // 제목
    val description: String,            // 설명
    val updatedDate: LocalDateTime,     // 갱신일시
    val visibility: Boolean,            // 공개여부
    val author: String,                 // 작성자
    val isBookmark: Boolean,            // 즐겨찾기 여부
    val songCount: Int,                 // 수록곡 수
    val isEditable: Boolean,            // 편집 가능 여부
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