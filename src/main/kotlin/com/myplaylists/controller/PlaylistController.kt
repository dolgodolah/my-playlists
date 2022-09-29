package com.myplaylists.controller

import com.myplaylists.config.auth.Login
import com.myplaylists.dto.*
import com.myplaylists.dto.PlaylistsDto.Companion.of
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
    fun createPlaylist(@RequestBody playlistRequestDto: PlaylistRequestDto, @Login user: LoginUser): BaseResponse {
        playlistService.createPlaylist(user.userId, playlistRequestDto)
        return BaseResponse.ok()
    }

    @GetMapping("/playlist/{playlistId}")
    fun checkBookmarkPlaylist(@PathVariable("playlistId") playlistId: Long, @Login user: LoginUser): BookmarkDto {
        return bookmarkService.checkBookmark(user.userId, playlistId)
    }

    @DeleteMapping("/playlist/{playlistId}")
    fun deletePlaylist(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long): BaseResponse {
        playlistService.deletePlaylist(user.userId, playlistId)
        return BaseResponse.ok()
    }

    @GetMapping("/bookmarks")
    fun getBookmarkPlaylists(
        @Login user: LoginUser,
        @PageableDefault(sort = ["createdDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        // TODO 1. 즐겨찾기 플리 조회도 author 가 고정이라서 from 으로 바꾸자
        // TODO 2. playlistService 에서 즐겨찾기 플리 조회하자
        val bookmarks = BookmarksDto.of(bookmarkService.findByUserId(user.userId, pageable))
        return of(bookmarks)
    }

    @PostMapping("/bookmark/{playlistId}")
    fun toggleBookmark(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long): BaseResponse {
        bookmarkService.toggleBookmark(user.userId, playlistId)
        return BaseResponse.ok()
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