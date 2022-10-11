package com.myplaylists.domain

import com.myplaylists.dto.SongUpdateRequestDto
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.ExceedLimitException
import java.util.*
import javax.persistence.*

const val MAX_SONG_COUNT = 100

@Entity
@Table(indexes = [Index(name = "idx_user_id", columnList = "userId")])
class Song(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "song_id")
    var id: Long? = null,

    val userId: Long,
    var title: String,
    val videoId: String,
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "playlist_id",
        nullable = false,
        foreignKey = ForeignKey(
            name = "fk_playlist_id_song",
            foreignKeyDefinition = "FOREIGN KEY (playlist_id) REFERENCES playlist(playlist_id) ON DELETE CASCADE"
        )
    )
    val playlist: Playlist,
): BaseTime() {

    fun updateSongDetail(song: SongUpdateRequestDto) {
        updateTitle(song.title)
        updateDescription(song.description)
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateDescription(description: String) {
        this.description = description
    }

    private fun isSameUser(userId: Long): Boolean {
        return this.userId == userId
    }

    /**
     * 해당 유저가 추가한 노래가 맞는지 검증
     */
    fun validateUser(userId: Long) {
        if (!isSameUser(userId)) {
            throw ApiException("잘못된 요청입니다.", 1)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val song = other as Song
        return id == song.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}

fun List<Song>.checkLimitCount() {
    if (this.size >= MAX_SONG_COUNT) {
        throw ExceedLimitException("수록곡은 최대 100개까지 담을 수 있습니다.")
    }
}