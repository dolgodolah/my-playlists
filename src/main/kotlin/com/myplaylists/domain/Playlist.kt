package com.myplaylists.domain

import com.myplaylists.exception.ApiException
import javax.persistence.*

@Entity
class Playlist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "playlist_id")
    val id: Long? = null,

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
    var songCount: Int = 0,
): BaseTime() {

    fun addSong() {
        songCount++
    }

    fun deleteSong() {
        songCount--
    }

    fun isSameUser(userId: Long) = user.id == userId

    fun validateUser(userId: Long) {
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }
}