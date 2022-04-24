package com.myplaylists.service

import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistsDto
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

    fun createPlaylist(userId: Long, playlistRequest: PlaylistRequestDto): Long {
        val user = userService.findByUserId(userId)
        val playlist = playlistRequest.toEntity(user)
        return playlistRepository.save(playlist).id
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

}