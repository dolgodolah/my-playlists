package com.myplaylists.domain

import com.myplaylists.exception.ApiException
import java.util.*
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
    var songCount: Int,
): BaseTime() {

    fun addSong(song: Song) {
        song.playlist = this
        song.userId = this.user.userId
        songCount++
    }

    fun deleteSong(song: Song) {
        songCount--
    }

    fun isSameUser(userId: Long) = user.userId == userId

    fun validateUser(userId: Long) {
        println(userId)
        println(user.userId)
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }
}