package com.myplaylists.service

import com.myplaylists.domain.Playlist
import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.dto.PlaylistsDto
import com.myplaylists.exception.ApiException
import com.myplaylists.repository.PlaylistRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlaylistService(
    private val userService: UserService,
    private val playlistRepository: PlaylistRepository
) {

    fun createPlaylist(userId: Long, playlistRequest: PlaylistRequestDto) {
        val user = userService.findByUserId(userId)
        val playlist = playlistRequest.toEntity(user)
        playlistRepository.save(playlist)
    }

    /**
     * 해당 페이지의 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findMyPlaylists(userId: Long, pageable: Pageable): PlaylistsDto {
        val user = userService.findByUserId(userId)
        val playlists = playlistRepository.findByUser(pageable, user)
        return PlaylistsDto.of(playlists)
    }

    /**
     * 해당 페이지의 모든 공개 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findAllPlaylists(pageable: Pageable): PlaylistsDto {
        val playlists = playlistRepository.findByVisibility(pageable, true)
        return PlaylistsDto.of(playlists)
    }

    @Transactional(readOnly = true)
    fun findPlaylistById(playlistId: Long): PlaylistResponseDto {
        val playlist = findPlaylistByIdOrElseThrow(playlistId)
        return PlaylistResponseDto.of(playlist)
    }

    fun deletePlaylist(userId: Long, playlistId: Long) {
        val playlist = findPlaylistByIdOrElseThrow(playlistId)
        playlist.validateUser(userId)
        playlistRepository.delete(playlist)
    }

    @Transactional(readOnly = true)
    fun searchMyPlaylists(userId: Long, pageable: Pageable, keyword: String): PlaylistsDto {
        val user = userService.findByUserId(userId)
        val playlists = playlistRepository.findByTitleContainingAndUser(pageable, keyword, user)
        return PlaylistsDto.of(playlists)
    }

    @Transactional(readOnly = true)
    fun searchAllPlaylists(pageable: Pageable, keyword: String): PlaylistsDto {
        val playlists = playlistRepository.findByVisibilityAndTitleContaining(pageable, true, keyword)
        return PlaylistsDto.of(playlists)
    }

    fun findPlaylistByIdOrElseThrow(playlistId: Long): Playlist =
        playlistRepository.findById(playlistId).orElseThrow { ApiException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }
}