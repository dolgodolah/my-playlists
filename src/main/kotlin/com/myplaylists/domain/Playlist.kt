package com.myplaylists.domain

import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.ExceedLimitException
import javax.persistence.*

const val MAX_PLAYLIST_COUNT = 50

@Entity
class Playlist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "playlist_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(
            name = "FK_USER_ID_PLAYLIST",
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE"
        )
    )
    val user: User,
    var title: String,
    var description: String,

    @Column(nullable = false)
    var visibility: Boolean = false,
): BaseTime() {

    companion object {
        fun of(playlist: PlaylistRequestDto, user: User): Playlist = Playlist(
                user = user,
                title = playlist.title,
                description = playlist.description,
                visibility = playlist.visibility
            )
    }

    fun isSameUser(userId: Long) = user.id == userId

    fun validateUser(userId: Long) {
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }

    fun toDTO(author: String, isBookmark: Boolean, songCount: Int): PlaylistResponseDto {
        return PlaylistResponseDto(
            playlistId = this.id!!,
            title = this.title,
            description = this.description,
            updatedDate = this.updatedDate,
            visibility = this.visibility,
            author = author,
            isBookmark = isBookmark,
            songCount = songCount
        )
    }
}

fun List<Playlist>.checkLimitCount() {
    if (this.size >= MAX_PLAYLIST_COUNT) {
        throw ExceedLimitException("플레이리스트는 최대 50개까지 생성 가능합니다.")
    }
}