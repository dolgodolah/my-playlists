package com.myplaylists.service

import com.myplaylists.domain.*
import com.myplaylists.dto.*
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.repository.cache.PlaylistCacheRepository
import com.myplaylists.repository.PlaylistJpaRepository
import com.myplaylists.utils.CryptoUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Comparator

// TODO: MyPlaylistService, AllPlaylistService 분리하자.
@Service
@Transactional
class PlaylistService(
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val songService: SongService,
    private val playlistJpaRepository: PlaylistJpaRepository,
    private val playlistCacheRepository: PlaylistCacheRepository,
    @Value("\${playlist.secret}") private val secretKey: String
) {

    companion object {
        const val PUBLIC = true
    }

    fun createPlaylist(userId: Long, playlistRequest: PlaylistAddRequestDto) {
        playlistCacheRepository.findAllByUserId(userId).checkLimitCount()
        playlistCacheRepository.create(
            playlist = Playlist.of(playlistRequest, userId),
            userId = userId
        )
    }

    fun updatePlaylist(userId: Long, playlistRequest: PlaylistUpdateRequestDto) {
        playlistCacheRepository.update(
            playlistId = playlistRequest.getDecryptedId(secretKey),
            playlistRequestDto = playlistRequest
        )
    }

    /**
     * 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findMyPlaylists(userId: Long): PlaylistsDto {
        val me = userService.findUserById(userId)
        val myPlaylists = playlistCacheRepository.findAllByUserId(userId)
            .sortByLatest()
            .toResponseDTO(
                isBookmark = { playlistId: Long -> bookmarkService.isBookmark(userId, playlistId) },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                myNickname = me.nickname,
                secretKey = secretKey
            )

        return PlaylistsDto.of(myPlaylists)
    }

    /**
     * 해당 페이지의 모든 공개 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findAllPlaylists(user: LoginUser?, pageable: Pageable): PlaylistsDto {
        val playlists = playlistJpaRepository.findByVisibility(pageable, PUBLIC)
            .map { playlist ->
                val author = userService.findUserById(playlist.userId).nickname
                val isBookmark = user?.let { bookmarkService.isBookmark(it.userId, playlist.id) } ?: false
                val isEditable = user?.let { playlist.userId == it.userId } ?: false
                val songCount = songService.getSongCount(playlist.id!!)
                val encryptedId = playlist.getEncryptedId(secretKey)
                playlist.toDTO(encryptedId, author, isBookmark, songCount, isEditable)
            }.toList()
        return PlaylistsDto.of(playlists)
    }

    /**
     * 즐겨찾기한 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findBookmarkPlaylists(userId: Long, pageable: Pageable): PlaylistsDto {
        val bookmarks = Bookmarks(bookmarkService.findByUserId(userId, pageable))
        val playlists = bookmarks.toPlaylists()
            .sortByLatest()
            .toResponseDTO(
                getNickname = { playlistUserId: Long -> userService.findUserById(playlistUserId).nickname },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                isEditable = { playlistUserId: Long -> userId == playlistUserId},
                secretKey = secretKey
            )

        return PlaylistsDto.of(playlists)
    }

    fun deletePlaylist(userId: Long, playlistId: Long) {
        playlistCacheRepository.deleteById(playlistId, userId)
    }

    @Transactional(readOnly = true)
    fun searchMyPlaylists(userId: Long, title: String): PlaylistsDto {
        val me = userService.findUserById(userId)
        val playlists = playlistCacheRepository.findAllByUserId(userId)
            .filterContainingTitle(title)
            .sortByLatest()
            .toResponseDTO(
                isBookmark = { playlistId: Long -> bookmarkService.isBookmark(userId, playlistId) },
                getSongCount = { playlistId: Long -> songService.getSongCount(playlistId) },
                myNickname = me.nickname,
                secretKey = secretKey
            )

        return PlaylistsDto.of(playlists)
    }

    @Transactional(readOnly = true)
    fun searchAllPlaylists(user: LoginUser, pageable: Pageable, title: String): PlaylistsDto {
        val playlists = playlistJpaRepository.findByVisibilityAndTitleContaining(pageable, true, title)
            .sortedWith(Comparator.comparing(Playlist::updatedDate).reversed())
            .map { playlist ->
                val author = userService.findUserById(playlist.userId).nickname
                val isBookmark = bookmarkService.isBookmark(user.userId, playlist.id)
                val songCount = songService.getSongCount(playlist.id!!)
                val isEditable = playlist.userId == user.userId
                val encryptedId = CryptoUtils.encrypt(playlist.id!!, secretKey)
                playlist.toDTO(encryptedId, author, isBookmark, songCount, isEditable)
            }.toList()
        return PlaylistsDto.of(playlists)
    }
}