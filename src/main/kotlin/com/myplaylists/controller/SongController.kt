package com.myplaylists.controller

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
    fun songsView(@Login user: LoginUser, @RequestParam p: Long): ResponseEntity<String> {
        val context = songService.createViewContext(user, p)
        return ViewResponse.ok().render("songs/songs.html", context = context)
    }

    @GetMapping("/songs/add")
    fun songAddView(@Login user: LoginUser, @RequestParam p: Long): ResponseEntity<String> {
        val context = songService.createViewContext(user, p)
        return ViewResponse.ok().render("songs/add.html", context = context)
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
    @PutMapping("/songs/{songId}")
    fun updateSong(
        @Login user: LoginUser,
        @PathVariable("songId") songId: Long,
        @RequestBody songRequestDto: SongUpdateRequestDto
    ) {
        songService.updateSong(user, songId, songRequestDto)
    }

    @ResponseBody
    @DeleteMapping("/songs/{songId}")
    fun deleteSong(@Login user: LoginUser, @PathVariable("songId") songId: Long) {
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
        @RequestParam playlistId: Long,
        @RequestParam q: String
    ): SongsDto {
        return songService.searchSongs(playlistId, q)
    }
}