package com.myplaylists.dto

import com.fasterxml.jackson.annotation.JsonProperty

class PlaylistRequestDto(
    val title: String,
    val description: String,
    val visibility: Boolean,
) {
}

data class PlaylistResponseDto(
    @JsonProperty("playlistId")
    val encryptedId: String,            // 암호화된 식별값
    val title: String,                  // 제목
    val description: String,            // 설명
    val updatedDate: String,            // 갱신일시
    val visibility: Boolean,            // 공개여부
    val author: String,                 // 작성자
    val isBookmark: Boolean,            // 즐겨찾기 여부
    val songCount: Int,                 // 수록곡 수
    val isEditable: Boolean,            // 편집 가능 여부
): BaseResponse() {
}

data class PlaylistsDto(
    val playlists: List<PlaylistResponseDto>,
): BaseResponse() {
    companion object {
        fun of(playlists: List<PlaylistResponseDto>): PlaylistsDto = PlaylistsDto(playlists = playlists)
    }
}