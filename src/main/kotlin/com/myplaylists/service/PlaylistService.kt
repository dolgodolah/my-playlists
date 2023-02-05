package com.myplaylists.service

import com.myplaylists.domain.Bookmark
import com.myplaylists.domain.Playlist
import com.myplaylists.domain.checkLimitCount
import com.myplaylists.dto.PlaylistRequestDto
import com.myplaylists.dto.PlaylistsDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.BadRequestException
import com.myplaylists.repository.PlaylistRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.util.Comparator

@Service
@Transactional
class PlaylistService(
    private val userService: UserService,
    private val bookmarkService: BookmarkService,
    private val songService: SongService,
    private val playlistRepository: PlaylistRepository,
) {

    companion object {
        const val PUBLIC = true
    }

    @CacheEvict(key = "#userId", value = ["playlist"])
    fun createPlaylist(userId: Long, playlistRequest: PlaylistRequestDto) {
        if (!StringUtils.hasText(playlistRequest.title)) {
            throw BadRequestException("플레이리스트 타이틀이 공백이거나 입력되지 않았습니다.")
        }

        playlistRepository.findAllByUserId(userId).checkLimitCount()

        val user = userService.findUserByIdOrElseThrow(userId)
        val playlist = Playlist.of(playlistRequest, user)
        playlistRepository.save(playlist)
    }

    /**
     * 해당 페이지의 내 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    @Cacheable(key = "#userId", value = ["playlist"])
    fun findMyPlaylists(userId: Long): PlaylistsDto {
        val user = userService.findUserById(userId)
        val playlists = playlistRepository.findAllByUserId(userId)
            .sortedWith(Comparator.comparing(Playlist::updatedDate).reversed())
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(userId, playlist.id)
                val songCount = songService.getSongCount(playlist.id!!)
                playlist.toDTO(user.nickname, isBookmark, songCount, isEditable = true)
            }.toList()

        return PlaylistsDto.of(playlists)
    }

    /**
     * 해당 페이지의 모든 공개 플레이리스트 목록을 업데이트 최신순으로 조회
     */
    @Transactional(readOnly = true)
    fun findAllPlaylists(user: LoginUser?, pageable: Pageable): PlaylistsDto {
        val playlists = playlistRepository.findByVisibility(pageable, PUBLIC)
            .sortedWith(Comparator.comparing(Playlist::updatedDate).reversed())
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(playlist.user.id, playlist.id)
                val songCount = songService.getSongCount(playlist.id!!)
                val isEditable = playlist.user.id == user?.let { it.userId }
                playlist.toDTO(playlist.user.nickname, isBookmark, songCount, isEditable)
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
                val songCount = songService.getSongCount(playlist.id!!)
                val isEditable = userId == playlist.user.id
                playlist.toDTO(playlist.user.nickname, isBookmark = true, songCount, isEditable)
            }.toList()

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
        val playlists = playlistRepository.findAllByUserIdAndTitleContaining(userId, keyword)
            .sortedWith(Comparator.comparing(Playlist::updatedDate).reversed())
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(userId, playlist.id)
                val songCount = songService.getSongCount(playlist.id!!)
                playlist.toDTO(user.nickname, isBookmark, songCount, isEditable = true)
            }.toList()

        return PlaylistsDto.of(playlists)
    }

    @Transactional(readOnly = true)
    fun searchAllPlaylists(user: LoginUser, pageable: Pageable, keyword: String): PlaylistsDto {
        val playlists = playlistRepository.findByVisibilityAndTitleContaining(pageable, true, keyword)
            .sortedWith(Comparator.comparing(Playlist::updatedDate).reversed())
            .map { playlist ->
                val isBookmark = bookmarkService.isBookmark(playlist.user.id, playlist.id)
                val songCount = songService.getSongCount(playlist.id!!)
                val isEditable = playlist.user.id == user.userId
                playlist.toDTO(playlist.user.nickname, isBookmark, songCount, isEditable)
            }.toList()
        return PlaylistsDto.of(playlists)
    }

    fun findPlaylistByIdOrElseThrow(playlistId: Long): Playlist =
        playlistRepository.findById(playlistId).orElseThrow { ApiException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.", 1) }
}