package com.myplaylists.dto

import com.myplaylists.domain.Playlist
import com.myplaylists.domain.User
import java.time.LocalDateTime
import java.util.Comparator

class PlaylistRequestDto(
    val title: String,
    private val description: String,
    private val visibility: Boolean,
) {
    fun toEntity(user: User): Playlist = Playlist(
        user = user,
        title = title,
        description = description,
        visibility = visibility
    )
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
    companion object {
        fun from(playlist: Playlist, author: String, isBookmark: Boolean, songCount: Int): PlaylistResponseDto {
            return PlaylistResponseDto(
                playlistId = playlist.id!!,
                title = playlist.title,
                description = playlist.description,
                updatedDate = playlist.updatedDate,
                visibility = playlist.visibility,
                author = author,
                isBookmark = isBookmark,
                songCount = songCount
            )
        }
    }
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