package com.myplaylists.service

import com.myplaylists.domain.Bookmark
import com.myplaylists.domain.Playlist
import com.myplaylists.dto.PlaylistCacheDTO
import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistsDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.checkLimitCount
import com.myplaylists.exception.BadRequestException
import com.myplaylists.repository.cache.PlaylistCacheRepository
import com.myplaylists.repository.PlaylistJpaRepository
import com.myplaylists.utils.CryptoUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.LocalDateTime
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

    fun createPlaylist(userId: Long, playlistRequest: PlaylistRequestDto) {
        if (!StringUtils.hasText(playlistRequest.title)) {
            throw BadRequestException("플레이리스트 타이틀이 공백이거나 입력되지 않았습니다.")
        }

        playlistCacheRepository.findAllByUserId(userId).checkLimitCount()
        playlistCacheRepository.create(
            playlist = Playlist.of(playlistRequest, userId),
            userId = userId
        )
    }

    fun updatePlaylist(userId: Long, playlistRequest: PlaylistRequestDto) {
        if (!StringUtils.hasText(playlistRequest.title)) {
            throw BadRequestException("플레이리스트 타이틀이 공백이거나 입력되지 않았습니다.")
        }

        playlistRequest.encryptedId?.let {
            val playlistId = CryptoUtils.decrypt(it, secretKey).toLong()
            playlistCacheRepository.update(playlistId, playlistRequest)
        } ?: throw BadRequestException()
    }

    /**
     * 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findMyPlaylists(userId: Long): PlaylistsDto {
        val user = userService.findUserById(userId)
        val playlists = playlistCacheRepository.findAllByUserId(userId)
            .sortedWith(Comparator.comparing(PlaylistCacheDTO::updatedDate).reversed())
            .map {
                val isBookmark = bookmarkService.isBookmark(userId, it.playlistId)
                val songCount = songService.getSongCount(it.playlistId)
                val encryptedId = CryptoUtils.encrypt(it.playlistId, secretKey)
                it.toDTO(encryptedId, user.nickname, isBookmark, songCount, isEditable = true)
            }

        return PlaylistsDto.of(playlists)
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
                val encryptedId = CryptoUtils.encrypt(playlist.id!!, secretKey)
                playlist.toDTO(encryptedId, author, isBookmark, songCount, isEditable)
            }.toList()
        return PlaylistsDto.of(playlists)
    }

    /**
     * 즐겨찾기한 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findBookmarkPlaylists(userId: Long, pageable: Pageable): PlaylistsDto {
        val playlists = bookmarkService.findByUserId(userId, pageable)
            .sortedWith(Comparator.comparing<Bookmark?, LocalDateTime?> { it.playlist.updatedDate }.reversed())
            .map { bookmark ->
                val playlist = bookmark.playlist
                val author = userService.findUserById(playlist.userId).nickname
                val songCount = songService.getSongCount(playlist.id!!)
                val isEditable = userId == playlist.userId
                val encryptedId = CryptoUtils.encrypt(playlist.id!!, secretKey)
                playlist.toDTO(encryptedId, author, isBookmark = true, songCount, isEditable)
            }.toList()

        return PlaylistsDto.of(playlists)
    }

    fun deletePlaylist(userId: Long, playlistId: Long) {
        playlistCacheRepository.deleteById(playlistId, userId)
    }

    @Transactional(readOnly = true)
    fun searchMyPlaylists(userId: Long, title: String): PlaylistsDto {
        val user = userService.findUserById(userId)
        val playlists = playlistCacheRepository.findAllByUserId(userId)
            .filter {
                it.title.contains(title, ignoreCase = true)
            }.sortedWith(Comparator.comparing(PlaylistCacheDTO::updatedDate).reversed())
            .map {
                val isBookmark = bookmarkService.isBookmark(userId, it.playlistId)
                val songCount = songService.getSongCount(it.playlistId)
                val encryptedId = CryptoUtils.encrypt(it.playlistId, secretKey)
                it.toDTO(encryptedId, user.nickname, isBookmark, songCount, isEditable = true)
            }.toList()

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