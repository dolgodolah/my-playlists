package com.myplaylists.service.playlist

import com.myplaylists.domain.Playlist
import com.myplaylists.dto.*
import com.myplaylists.repository.cache.PlaylistCacheRepository
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.SongService
import com.myplaylists.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MyPlaylistService(
    private val playlistCacheRepository: PlaylistCacheRepository,
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val songService: SongService,
    @Value("\${playlist.secret}") private val secretKey: String
) {

    fun createPlaylist(userId: Long, playlistRequest: PlaylistAddRequestDto) {
        playlistCacheRepository.findAllByUserId(userId).checkLimitCount()
        playlistCacheRepository.create(
            playlist = Playlist.of(playlistRequest, userId),
            userId = userId
        )
    }

    /**
     * 내 플레이리스트 목록 조회
     */
    @Transactional(readOnly = true)
    fun findMyPlaylists(userId: Long): PlaylistsDto {
        val me = userService.findUserById(userId)
        val myPlaylists = findSortedMyPlaylistsByLatest(userId)
            .toResponseDTO(
                isBookmark = { playlistId: Long -> bookmarkService.isBookmark(userId, playlistId) },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                myNickname = me.nickname,
                secretKey = secretKey,
                isEditable = true
            )
        return PlaylistsDto.of(myPlaylists)
    }

    /**
     * 내 플레이리스트 목록을 제목으로 조회
     */
    @Transactional(readOnly = true)
    fun findMyPlaylistsByTitle(userId: Long, title: String): PlaylistsDto {
        val me = userService.findUserById(userId)
        val myPlaylists = findSortedMyPlaylistsByLatest(userId)
            .toResponseDTO(
                isBookmark = { playlistId: Long -> bookmarkService.isBookmark(userId, playlistId) },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                myNickname = me.nickname,
                secretKey = secretKey,
                isEditable = true
            ).filterContainingTitle(title)
        return PlaylistsDto.of(myPlaylists)
    }

    /**
     * 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    private fun findSortedMyPlaylistsByLatest(userId: Long): List<PlaylistCacheDTO> {
        return playlistCacheRepository.findAllByUserId(userId).sortByLatest()
    }

    fun updatePlaylist(userId: Long, playlistRequest: PlaylistUpdateRequestDto) {
        playlistCacheRepository.findById(
            playlistId = playlistRequest.getDecryptedId(secretKey)
        ).throwIfNotMyPlaylist(userId)

        playlistCacheRepository.update(
            playlistId = playlistRequest.getDecryptedId(secretKey),
            playlistRequestDto = playlistRequest
        )
    }

    fun deletePlaylist(userId: Long, playlistId: Long) {
        playlistCacheRepository.findById(
            playlistId = playlistId
        ).throwIfNotMyPlaylist(userId)

        playlistCacheRepository.deletePlaylistCache(playlistId, userId)
    }
}