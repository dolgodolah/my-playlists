package com.myplaylists.domain

import com.myplaylists.dto.*
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.ExceedLimitException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import javax.persistence.*

const val MAX_PLAYLIST_COUNT = 50

@Entity
class Playlist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "playlist_id")
    var id: Long? = null,
    val userId: Long,
    var title: String,
    var description: String,

    @Column(nullable = false)
    var visibility: Boolean = false,
): BaseTime() {

    companion object {
        val DATE_DISPLAY_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            .withResolverStyle(ResolverStyle.STRICT)
        fun of(playlist: PlaylistAddRequestDto, userId: Long): Playlist = Playlist(
                userId = userId,
                title = playlist.title,
                description = playlist.description,
                visibility = playlist.visibility
            )
    }

    fun isSameUser(userId: Long) = this.userId == userId

    fun validateUser(userId: Long) {
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }

    fun updateTitleAndDescription(playlist: PlaylistUpdateRequestDto): Playlist {
        this.title = playlist.title
        this.description = playlist.description
        return this
    }

    fun toCacheDTO(updatedDate: LocalDateTime = this.updatedDate): PlaylistCacheDTO {
        return PlaylistCacheDTO(
            playlistId = this.id!!,
            userId = this.userId,
            title = this.title,
            description = this.description,
            updatedDate = updatedDate,
            createdDate = this.createdDate,
            visibility = this.visibility
        )
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

fun List<Playlist>.checkLimitCount() {
    if (this.size >= MAX_PLAYLIST_COUNT) {
        throw ExceedLimitException("플레이리스트는 최대 50개까지 생성 가능합니다.")
    }
}