package com.myplaylists.service.playlist

import com.myplaylists.domain.Playlist
import com.myplaylists.dto.PlaylistsDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.repository.PlaylistJpaRepository
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.SongService
import com.myplaylists.service.UserService
import com.myplaylists.utils.CryptoUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Comparator

@Service
@Transactional
class AllPlaylistService(
    private val playlistJpaRepository: PlaylistJpaRepository,
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val songService: SongService,
    @Value("\${playlist.secret}") private val secretKey: String
) {

    companion object {
        const val PUBLIC = true
    }

    /**
     * 모든 플레이리스트 목록을 업데이트 최신순으로 조회
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
     * 모든 플레이리스트 목록을 제목으로 조회
     */
    @Transactional(readOnly = true)
    fun findAllPlaylistsByTitle(user: LoginUser, pageable: Pageable, title: String): PlaylistsDto {
        val playlists = playlistJpaRepository.findByVisibilityAndTitleContaining(pageable, PUBLIC, title)
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