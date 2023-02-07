package com.myplaylists.service

import com.myplaylists.client.GoogleClient
import com.myplaylists.domain.Song
import com.myplaylists.domain.checkLimitCount
import com.myplaylists.domain.toDTO
import com.myplaylists.dto.SongAddRequestDto
import com.myplaylists.dto.SongUpdateRequestDto
import com.myplaylists.dto.SongsDto
import com.myplaylists.dto.YoutubeDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.context.SongsViewContext
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.BadRequestException
import com.myplaylists.exception.NotFoundException
import com.myplaylists.repository.PlaylistRepository
import com.myplaylists.repository.SongRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class SongService(
    private val songRepository: SongRepository,
    private val playlistRepository: PlaylistRepository,
    private val googleClient: GoogleClient,
    private val bookmarkService: BookmarkService,
) {

    @Transactional(readOnly = true)
    fun createViewContext(user: LoginUser?, playlistId: Long): SongsViewContext {
        val playlist = playlistRepository.findById(playlistId).orElseThrow { NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }
        val songs = songRepository.findAllByPlaylistId(playlistId)
        val isBookmark = user?.let { bookmarkService.isBookmark(it.userId, playlistId) } ?: false
        val isEditable = user?.let { playlist.user.id == it.userId } ?: false

        return SongsViewContext(
            songs = songs.toDTO().songs,
            currentPlaylist = playlist.toDTO(
                playlist.user.nickname,
                isBookmark,
                songs.size,
                isEditable
            )
        )
    }

    @CacheEvict(key = "#user.userId", value = ["playlist"])
    fun addSong(user: LoginUser, songRequestDto: SongAddRequestDto): Long {
        val playlist = playlistRepository.findById(songRequestDto.playlistId).orElseThrow { NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }
        playlist.validateUser(user.userId)

        songRepository.findAllByPlaylistId(playlist.id).checkLimitCount()

        val song = songRepository.save(
            Song.of(
                song = songRequestDto,
                userId = user.userId,
                playlist = playlist
            )
        )

        playlist.updatedDate = LocalDateTime.now()
        return song.id!!
    }

    fun updateSong(user: LoginUser, songId: Long, songRequestDto: SongUpdateRequestDto) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)
        song.updateSongDetail(songRequestDto)
    }

    @CacheEvict(key = "#user.userId", value = ["playlist"])
    fun deleteSong(user: LoginUser, songId: Long) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)

        songRepository.delete(song)
    }

    @Transactional(readOnly = true)
    fun findSongsByPlaylistId(playlistId: Long): SongsDto {
        val playlist = playlistRepository.findById(playlistId).orElseThrow{ NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }

        if (!playlist.visibility) {
            throw BadRequestException("해당 플레이리스트는 비공개 플레이리스트입니다.")
        }

        return songRepository.findAllByPlaylistId(playlistId).toDTO()
    }

    @Transactional(readOnly = true)
    fun searchSongs(playlistId: Long, title: String): SongsDto {
        val playlist = playlistRepository.findById(playlistId).orElseThrow { NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }
        return songRepository.findByPlaylistAndTitleContaining(playlist, title).toDTO()
    }

    fun getSongCount(playlistId: Long): Int = songRepository.findAllByPlaylistId(playlistId).size

    fun searchYoutube(q: String): YoutubeDto = googleClient.searchYoutube(q)

    private fun findSongByIdOrElseThrow(songId: Long): Song = songRepository.findById(songId).orElseThrow { NotFoundException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다.") }

}