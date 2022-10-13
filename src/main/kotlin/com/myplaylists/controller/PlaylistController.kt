package com.myplaylists.controller

import com.myplaylists.config.auth.Login
import com.myplaylists.dto.*
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.PlaylistService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
class PlaylistController(
    private val playlistService: PlaylistService,
    private val bookmarkService: BookmarkService
) {

    @GetMapping("/my_playlists")
    fun getMyPlaylists(@Login user: LoginUser): PlaylistsDto {
        return playlistService.findMyPlaylists(user.userId)
    }

    @GetMapping("/all_playlists")
    fun getAllPlaylists(
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.findAllPlaylists(pageable)
    }

    @PostMapping("/playlist")
    fun createPlaylist(@RequestBody playlistRequestDto: PlaylistRequestDto, @Login user: LoginUser) {
        playlistService.createPlaylist(user.userId, playlistRequestDto)
    }

    @DeleteMapping("/playlist/{playlistId}")
    fun deletePlaylist(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long) {
        playlistService.deletePlaylist(user.userId, playlistId)
    }

    @GetMapping("/bookmarks")
    fun getBookmarkPlaylists(
        @Login user: LoginUser,
        @PageableDefault(sort = ["createdDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.findBookmarkPlaylists(user.userId, pageable)
    }

    @PostMapping("/bookmark/{playlistId}")
    fun toggleBookmark(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long) {
        bookmarkService.toggleBookmark(user.userId, playlistId)
    }

    @GetMapping("/playlist/search")
    fun searchMyPlaylists(
        @Login user: LoginUser,
        @RequestParam keyword: String,
    ): PlaylistsDto {
        return playlistService.searchMyPlaylists(user.userId, keyword)
    }

    @GetMapping("/playlist/search_all")
    fun searchAllPlaylists(
        keyword: String,
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.searchAllPlaylists(pageable, keyword)
    }
}