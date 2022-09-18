package com.myplaylists.dto

import com.myplaylists.domain.Bookmark
import com.myplaylists.domain.Playlist
import com.myplaylists.domain.User
import org.springframework.data.domain.Page
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Comparator
import java.util.stream.Collectors

class PlaylistRequestDto(
    private val title: String,
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
    val songCount: Int
): BaseResponse() {
    companion object {
        fun of(playlist: Playlist): PlaylistResponseDto {
            return PlaylistResponseDto(
                playlistId = playlist.id!!,
                title = playlist.title,
                description = playlist.description,
                updatedDate = playlist.updatedDate,
                visibility = playlist.visibility,
                author = playlist.user.nickname,
                songCount = playlist.songCount
            )
        }

        /**
         * `내 플레이리스트`처럼 `author`가 하나로 고정되어 있는 경우 사용
         */
        fun from(playlist: Playlist, author: String): PlaylistResponseDto {
            return PlaylistResponseDto(
                playlistId = playlist.id!!,
                title = playlist.title,
                description = playlist.description,
                updatedDate = playlist.updatedDate,
                visibility = playlist.visibility,
                author = author,
                songCount = playlist.songCount
            )
        }
    }
}

class PlaylistsDto(
    val playlists: List<PlaylistResponseDto>,
    val isLast: Boolean,
): BaseResponse() {
    companion object {
        fun of(playlists: Page<Playlist>): PlaylistsDto {
            val playlistDtoList = playlists.stream()
                .map(PlaylistResponseDto::of)
                .sorted(Comparator.comparing(PlaylistResponseDto::updatedDate).reversed())
                .collect(Collectors.toList())

            return PlaylistsDto(
                playlists = playlistDtoList,
                isLast = playlists.isLast
            )
        }

        /**
         * `내 플레이리스트`처럼 `author`가 하나로 고정되어 있는 경우 사용
         */
        fun from(playlists: Page<Playlist>, author: String): PlaylistsDto {
            val playlistDtoList = playlists.stream()
                .map { playlist -> PlaylistResponseDto.from(playlist, author) }
                .sorted(Comparator.comparing(PlaylistResponseDto::updatedDate).reversed())
                .collect(Collectors.toList())

            return PlaylistsDto(
                playlists = playlistDtoList,
                isLast = playlists.isLast
            )
        }

        fun of(bookmarksDto: BookmarksDto): PlaylistsDto {
            val playlistDtoList = bookmarksDto.bookmarks.stream()
                .map(Bookmark::playlist)
                .map(PlaylistResponseDto::of)
                .sorted(Comparator.comparing(PlaylistResponseDto::updatedDate).reversed())
                .collect(Collectors.toList())

            return PlaylistsDto(
                playlists = playlistDtoList,
                isLast = bookmarksDto.isLast
            )
        }
    }
}