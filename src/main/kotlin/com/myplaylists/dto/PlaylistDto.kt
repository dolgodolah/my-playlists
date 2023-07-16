package com.myplaylists.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.myplaylists.exception.ExceedLimitException
import com.myplaylists.utils.CryptoUtils
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import javax.validation.constraints.NotBlank

const val MAX_PLAYLIST_COUNT = 50

data class PlaylistAddRequestDto(
    @field:NotBlank(message = "플레이리스트 타이틀이 공백이거나 입력되지 않았습니다.")
    val title: String,
    val description: String,
    val visibility: Boolean,
)

data class PlaylistUpdateRequestDto(
    @JsonProperty("playlistId")
    val encryptedId: String,
    @field:NotBlank(message = "플레이리스트 타이틀이 공백이거나 입력되지 않았습니다.")
    val title: String,
    val description: String,
    val visibility: Boolean,
) {
    fun getDecryptedId(secretKey: String): Long = CryptoUtils.decrypt(this.encryptedId, secretKey).toLong()

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

data class PlaylistCacheDTO(
    val playlistId: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val updatedDate: LocalDateTime,
    val createdDate: LocalDateTime,
    val visibility: Boolean
) {
    companion object {
        val DATE_DISPLAY_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            .withResolverStyle(ResolverStyle.STRICT)
    }

    fun toDTO(encryptedId: String, author: String, isBookmark: Boolean, songCount: Int, isEditable: Boolean): PlaylistResponseDto {
        return PlaylistResponseDto(
            encryptedId = encryptedId,
            title = this.title,
            description = this.description,
            updatedDate = DATE_DISPLAY_FORMATTER.format(this.updatedDate),
            visibility = this.visibility,
            author = author,
            isBookmark = isBookmark,
            songCount = songCount,
            isEditable = isEditable
        )
    }
}

fun List<PlaylistCacheDTO>.checkLimitCount() {
    if (this.size >= MAX_PLAYLIST_COUNT) {
        throw ExceedLimitException("플레이리스트는 최대 50개까지 생성 가능합니다.")
    }
}