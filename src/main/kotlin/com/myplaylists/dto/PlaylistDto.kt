package com.myplaylists.dto

import com.myplaylists.domain.Bookmark
import com.myplaylists.domain.Playlist
import com.myplaylists.domain.User
import org.springframework.data.domain.Page
import java.time.format.DateTimeFormatter
import java.util.Comparator
import java.util.stream.Collectors

class PlaylistRequestDto(
    private val title: String,
    private val description: String,
    private val visibility: Boolean,
) {
    fun toEntity(user: User): Playlist = Playlist(user, title, description, visibility)
}

class PlaylistResponseDto(
    val playlistId: Long,
    val title: String,
    val description: String,
    val updatedDate: String,
    val visibility: Boolean,
    val author: String,
    val songCount: Int
): BaseResponse() {
    companion object {
        fun of(playlist: Playlist): PlaylistResponseDto {
            return PlaylistResponseDto(
                playlistId = playlist.id,
                title = playlist.title,
                description = playlist.description,
                updatedDate = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(playlist.updatedDate),
                visibility = playlist.visibility,
                author = playlist.author,
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

        fun of(bookmarksDto: BookmarksDto): PlaylistsDto {
            val playlistDtoList = bookmarksDto.bookmarks.stream()
                .map(Bookmark::playlist)
                .map(PlaylistResponseDto::of)
                .collect(Collectors.toList())

            return PlaylistsDto(
                playlists = playlistDtoList,
                isLast = bookmarksDto.isLast
            )
        }
    }
}