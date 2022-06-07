package com.myplaylists.service

import com.myplaylists.domain.Song
import com.myplaylists.dto.SongRequestDto
import com.myplaylists.dto.SongsDto
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.exception.ApiException
import com.myplaylists.exception.NotFoundException
import com.myplaylists.repository.SongRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SongService(
    private val songRepository: SongRepository,
    private val playlistService: PlaylistService
) {

    fun addSong(user: LoginUser, songRequestDto: SongRequestDto): Long {
        val song = Song.builder()
            .title(songRequestDto.title)
            .videoId(songRequestDto.videoId)
            .build()
        val playlist = playlistService.findPlaylistByIdOrElseThrow(songRequestDto.playlistId)

        playlist.validateUser(user.userId)
        playlist.addSong(song)
        return songRepository.save(song).id
    }

    fun updateSong(user: LoginUser, songId: Long, songRequestDto: SongRequestDto) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)
        song.updateSongDetail(songRequestDto)
    }

    fun deleteSong(user: LoginUser, songId: Long) {
        val song = findSongByIdOrElseThrow(songId)
        song.validateUser(user.userId)

        val playlist = song.playlist
        playlist.deleteSong(song)
        songRepository.delete(song)
    }

    @Transactional(readOnly = true)
    fun findSongByPlaylistId(playlistId: Long): SongsDto {
        val playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId)
        return SongsDto.of(songRepository.findSongsByPlaylist(playlist))
    }

    private fun findSongByIdOrElseThrow(songId: Long): Song {
        return songRepository.findById(songId).orElseThrow { NotFoundException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다.") }
    }
}