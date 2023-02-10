package com.myplaylists.controller

import com.myplaylists.crypto.Decrypted
import com.myplaylists.config.auth.Login
import com.myplaylists.dto.*
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.service.SongService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class SongController(
    private val songService: SongService,
) {

    /**
     * View Controller
     */
    @GetMapping("/songs")
    fun songsView(@Login user: LoginUser?, @RequestParam p: String, @Decrypted playlistId: Long): ResponseEntity<String> {
        val context = songService.createViewContext(user, playlistId)
        return ViewResponse.ok().render("songs/songs.html", context = context)
    }

    /**
     * API Controller
     */
    @ResponseBody
    @PostMapping("/songs")
    fun addSong(@Login user: LoginUser, @RequestBody songRequestDto: SongAddRequestDto) {
        songService.addSong(user, songRequestDto)
    }


    @ResponseBody
    @GetMapping("/refresh-songs")
    fun findSongsByPlaylistId(@Login user: LoginUser, @RequestParam p: String, @Decrypted playlistId: Long): SongsDto {
        return songService.findSongsByPlaylistId(playlistId, user.userId)
    }

    @ResponseBody
    @PutMapping("/songs")
    fun updateSong(
        @Login user: LoginUser,
        @RequestParam("songId") songId: Long,
        @RequestBody songRequestDto: SongUpdateRequestDto
    ) {
        songService.updateSong(user, songId, songRequestDto)
    }

    @ResponseBody
    @DeleteMapping("/songs")
    fun deleteSong(@Login user: LoginUser, @RequestParam("songId") songId: Long) {
        songService.deleteSong(user, songId)
    }

    @ResponseBody
    @GetMapping("/youtube-search")
    fun searchYoutube(@Login user: LoginUser, @RequestParam q: String): YoutubeDto {
        return songService.searchYoutube(q)
    }

    @ResponseBody
    @GetMapping("/songs/search")
    fun searchSongs(
        @Login user: LoginUser,
        @RequestParam p: String,
        @Decrypted playlistId: Long,
        @RequestParam q: String
    ): SongsDto {
        return songService.searchSongs(playlistId, q)
    }
}