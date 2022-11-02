package com.myplaylists.domain

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

    fun isSameUser(userId: Long) = user.id == userId

    fun validateUser(userId: Long) {
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }
}

fun List<Playlist>.checkLimitCount() {
    if (this.size >= MAX_PLAYLIST_COUNT) {
        throw ExceedLimitException("플레이리스트는 최대 50개까지 생성 가능합니다.")
    }
}