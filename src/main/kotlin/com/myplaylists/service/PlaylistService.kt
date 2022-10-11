package com.myplaylists.service

import com.myplaylists.domain.Playlist
import com.myplaylists.domain.checkLimitCount
import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.dto.PlaylistsDto
import com.myplaylists.exception.ApiException
import com.myplaylists.repository.PlaylistRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class PlaylistService(
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val playlistRepository: PlaylistRepository,
) {

    companion object {
        const val PUBLIC = true
    }

    @CacheEvict(key = "#userId", value = ["playlist"])
    fun createPlaylist(userId: Long, playlistRequest: PlaylistRequestDto) {
        playlistRepository.findByUserId(userId).checkLimitCount()

        val user = userService.findUserByIdOrElseThrow(userId)
        val playlist = playlistRequest.toEntity(user)
        playlistRepository.save(playlist)
    }

    /**
     * 해당 페이지의 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "#userId", value = ["playlist"])
    fun findMyPlaylists(userId: Long): PlaylistsDto {
        val user = userService.findUserById(userId)
        val playlists = playlistRepository.findByUserId(userId).stream()
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(userId, playlist.id)
                PlaylistResponseDto.from(playlist, user.nickname, isBookmark)
            }.collect(Collectors.toList())

        return PlaylistsDto.of(playlists)
    }

    /**
     * 해당 페이지의 모든 공개 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findAllPlaylists(pageable: Pageable): PlaylistsDto {
        val playlists = playlistRepository.findByVisibility(pageable, PUBLIC).stream()
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(playlist.user.id, playlist.id)
                PlaylistResponseDto.from(playlist, playlist.user.nickname, isBookmark)
            }.collect(Collectors.toList())
        return PlaylistsDto.of(playlists)
    }

    /**
     * 즐겨찾기한 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findBookmarkPlaylists(userId: Long, pageable: Pageable): PlaylistsDto {
        val playlists = bookmarkService.findByUserId(userId, pageable).stream()
            .map { bookmark ->
                val playlist = bookmark.playlist
                PlaylistResponseDto.from(playlist, playlist.user.nickname, isBookmark = true)
            }.collect(Collectors.toList())

        return PlaylistsDto.of(playlists)
    }

    @CacheEvict(key = "#userId", value = ["playlist"])
    fun deletePlaylist(userId: Long, playlistId: Long) {
        val playlist = findPlaylistByIdOrElseThrow(playlistId)
        playlist.validateUser(userId)
        playlistRepository.delete(playlist)
    }

    @Transactional(readOnly = true)
    fun searchMyPlaylists(userId: Long, keyword: String): PlaylistsDto {
        val user = userService.findUserById(userId)
        val playlists = playlistRepository.findByUserIdAndTitleContaining(userId, keyword).stream()
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(userId, playlist.id)
                PlaylistResponseDto.from(playlist, user.nickname, isBookmark)
            }.collect(Collectors.toList())

        return PlaylistsDto.of(playlists)
    }

    @Transactional(readOnly = true)
    fun searchAllPlaylists(pageable: Pageable, keyword: String): PlaylistsDto {
        val playlists = playlistRepository.findByVisibilityAndTitleContaining(pageable, true, keyword).stream()
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(playlist.user.id, playlist.id)
                PlaylistResponseDto.from(playlist, playlist.user.nickname, isBookmark)
            }.collect(Collectors.toList())
        return PlaylistsDto.of(playlists)
    }

    fun findPlaylistByIdOrElseThrow(playlistId: Long): Playlist =
        playlistRepository.findById(playlistId).orElseThrow { ApiException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.", 1) }
}