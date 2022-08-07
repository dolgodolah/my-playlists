package com.myplaylists.service

import com.myplaylists.domain.Song
import com.myplaylists.dto.SongAddRequestDto
import com.myplaylists.dto.SongUpdateRequestDto
import com.myplaylists.dto.SongsDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.exception.NotFoundException
import com.myplaylists.repository.SongRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SongService(
    private val songRepository: SongRepository,
    private val playlistService: PlaylistService,
    private val userService: UserService,
) {

    fun addSong(user: LoginUser, songRequestDto: SongAddRequestDto): Long {
        val playlist = playlistService.findPlaylistByIdOrElseThrow(songRequestDto.playlistId)
        playlist.validateUser(user.userId)
        playlist.addSong()

        val song = songRequestDto.toEntity(userId = user.userId, playlist = playlist)

        return songRepository.save(song).id!!
    }

    fun updateSong(user: LoginUser, songId: Long, songRequestDto: SongUpdateRequestDto) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)
        song.updateSongDetail(songRequestDto)
    }

    fun deleteSong(user: LoginUser, songId: Long) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)

        val playlist = song.playlist
        playlist.deleteSong()
        songRepository.delete(song)
    }

    @Transactional(readOnly = true)
    fun findSongsByPlaylistId(playlistId: Long): SongsDto {
        val playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId)
        return SongsDto.of(songRepository.findSongsByPlaylist(playlist))
    }

    @Transactional(readOnly = true)
    fun searchSongs(playlistId: Long, keyword: String): SongsDto {
        val playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId)
        val songs = songRepository.findByPlaylistAndTitleContaining(playlist, keyword)
        return SongsDto.of(songs)
    }

    private fun findSongByIdOrElseThrow(songId: Long): Song {
        return songRepository.findById(songId).orElseThrow { NotFoundException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다.") }
    }
}