package com.myplaylists.service.playlist

import com.myplaylists.domain.*
import com.myplaylists.dto.*
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.SongService
import com.myplaylists.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookmarkPlaylistService(
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val songService: SongService,
    @Value("\${playlist.secret}") private val secretKey: String
) {

    /**
     * 즐겨찾기한 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findBookmarkPlaylists(userId: Long, pageable: Pageable): PlaylistsDto {
        val bookmarks = Bookmarks(bookmarkService.findByUserId(userId, pageable))
        val playlists = bookmarks.toPlaylists()
            .sortByLatest()
            .toResponseDTO(
                isBookmark = true,
                getNickname = { playlistUserId: Long -> userService.findUserById(playlistUserId).nickname },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                isEditable = { playlistUserId: Long -> userId == playlistUserId},
                secretKey = secretKey
            )

        return PlaylistsDto.of(playlists)
    }
}