package com.myplaylists.controller

import com.myplaylists.config.auth.Login
import com.myplaylists.dto.*
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.context.PlaylistsViewContext
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.PlaylistService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class PlaylistController(
    private val playlistService: PlaylistService,
    private val bookmarkService: BookmarkService
) {

    companion object {
        const val MY_PLAYLISTS = "my-playlists"
        const val ALL_PLAYLISTS = "all-playlists"
        const val BOOKMARKS = "bookmarks"
    }

    /**
     * View Controller
     */
    @GetMapping("/playlists")
    fun playlistView(@Login user: LoginUser): ResponseEntity<String> {
        val playlistsDto = playlistService.findMyPlaylists(user.userId)
        val context = PlaylistsViewContext(page = MY_PLAYLISTS, playlists = playlistsDto.playlists)

        return ViewResponse.ok().render("playlists/playlists.html", context = context)
    }

    @GetMapping("/playlists/add")
    fun playlistAddView(@Login user: LoginUser): ResponseEntity<String> {
        val playlistsDto = playlistService.findMyPlaylists(user.userId)
        val context = PlaylistsViewContext(page = MY_PLAYLISTS, playlists = playlistsDto.playlists)
        return ViewResponse.ok().render("playlists/add.html", context = context)
    }


    /**
     * API Controller
     */
    @ResponseBody
    @GetMapping("/my-playlists")
    fun getMyPlaylists(@Login user: LoginUser): PlaylistsDto {
        return playlistService.findMyPlaylists(user.userId)
    }

    @ResponseBody
    @GetMapping("/all-playlists")
    fun getAllPlaylists(
        @Login user: LoginUser?, // 비회원도 모든 플레이리스트 최신 2페이지는 조회 가능하므로 nullable
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.findAllPlaylists(user, pageable)
    }

    @ResponseBody
    @PostMapping("/playlists")
    fun createPlaylist(@RequestBody playlistRequestDto: PlaylistRequestDto, @Login user: LoginUser) {
        playlistService.createPlaylist(user.userId, playlistRequestDto)
    }

    @ResponseBody
    @DeleteMapping("/playlists/{playlistId}")
    fun deletePlaylist(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long) {
        playlistService.deletePlaylist(user.userId, playlistId)
    }

    @ResponseBody
    @GetMapping("/bookmarks")
    fun getBookmarkPlaylists(
        @Login user: LoginUser,
        @PageableDefault(sort = ["createdDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.findBookmarkPlaylists(user.userId, pageable)
    }

    @ResponseBody
    @PostMapping("/bookmarks/{playlistId}")
    fun toggleBookmark(@Login user: LoginUser, @PathVariable("playlistId") playlistId: Long) {
        bookmarkService.toggleBookmark(user.userId, playlistId)
    }

    @ResponseBody
    @GetMapping("/playlists/search")
    fun searchMyPlaylists(
        @Login user: LoginUser,
        @RequestParam keyword: String,
    ): PlaylistsDto {
        return playlistService.searchMyPlaylists(user.userId, keyword)
    }

    @ResponseBody
    @GetMapping("/playlists/search-all")
    fun searchAllPlaylists(
        @Login user: LoginUser,
        keyword: String,
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return playlistService.searchAllPlaylists(user, pageable, keyword)
    }
}