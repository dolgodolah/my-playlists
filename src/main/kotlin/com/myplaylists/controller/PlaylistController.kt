package com.myplaylists.controller

import com.myplaylists.config.auth.Login
import com.myplaylists.crypto.Decrypted
import com.myplaylists.dto.*
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.context.PlaylistsViewContext
import com.myplaylists.service.BookmarkService
import com.myplaylists.service.playlist.BookmarkPlaylistService
import com.myplaylists.service.playlist.AllPlaylistService
import com.myplaylists.service.playlist.MyPlaylistService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@Controller
class PlaylistController(
    private val myPlaylistService: MyPlaylistService,
    private val allPlaylistService: AllPlaylistService,
    private val bookmarkPlaylistService: BookmarkPlaylistService,
    private val bookmarkService: BookmarkService,
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
    fun playlistView(@Login user: LoginUser?): ResponseEntity<String> {
        val category = user?.let { MY_PLAYLISTS } ?: ALL_PLAYLISTS
        val playlistsDto = user?.let {
            myPlaylistService.findMyPlaylists(user.userId)
        } ?: allPlaylistService.findAllPlaylists(null, PageRequest.of(0, 10, Sort.Direction.DESC, "updatedDate"))

        val context = PlaylistsViewContext(category = category, playlists = playlistsDto.playlists, isGuest = user == null)
        return ViewResponse.ok().render("playlists/playlists.html", context = context)
    }

    @GetMapping("/playlists/add")
    fun playlistAddView(@Login user: LoginUser): ResponseEntity<String> {
        val playlistsDto = myPlaylistService.findMyPlaylists(user.userId)
        val context = PlaylistsViewContext(category = MY_PLAYLISTS, playlists = playlistsDto.playlists)
        return ViewResponse.ok().render("playlists/add.html", context = context)
    }


    /**
     * API Controller
     */
    @ResponseBody
    @GetMapping("/my-playlists")
    fun getMyPlaylists(@Login user: LoginUser): PlaylistsDto {
        return myPlaylistService.findMyPlaylists(user.userId)
    }

    @ResponseBody
    @GetMapping("/all-playlists")
    fun getAllPlaylists(
        @Login user: LoginUser?, // 비회원도 모든 플레이리스트 최신 2페이지는 조회 가능하므로 nullable
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return allPlaylistService.findAllPlaylists(user, pageable)
    }

    @ResponseBody
    @PostMapping("/playlists")
    fun createPlaylist(@RequestBody @Valid playlistRequestDto: PlaylistAddRequestDto, @Login user: LoginUser) {
        myPlaylistService.createPlaylist(user.userId, playlistRequestDto)
    }

    @ResponseBody
    @PutMapping("/playlists")
    fun updatePlaylist(@RequestBody @Valid playlistRequestDto: PlaylistUpdateRequestDto, @Login user: LoginUser) {
        myPlaylistService.updatePlaylist(user.userId, playlistRequestDto)
    }

    @ResponseBody
    @DeleteMapping("/playlists")
    fun deletePlaylist(@Login user: LoginUser, @RequestParam p: String, @Decrypted playlistId: Long) {
        myPlaylistService.deletePlaylist(user.userId, playlistId)
    }

    @ResponseBody
    @GetMapping("/bookmarks")
    fun getBookmarkPlaylists(
        @Login user: LoginUser,
        @PageableDefault(sort = ["createdDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return bookmarkPlaylistService.findBookmarkPlaylists(user.userId, pageable)
    }

    @ResponseBody
    @GetMapping("/count-bookmarks")
    fun getBookmarkCount(
        @RequestParam p: String,
        @Decrypted playlistId: Long
    ): BookmarkDto {
        return BookmarkDto(bookmarkService.getBookmarkCount(playlistId))
    }

    @ResponseBody
    @PostMapping("/bookmarks")
    fun toggleBookmark(@Login user: LoginUser, @RequestParam p: String, @Decrypted playlistId: Long) {
        bookmarkService.toggleBookmark(user.userId, playlistId)
    }

    @ResponseBody
    @GetMapping("/playlists/search")
    fun searchMyPlaylists(
        @Login user: LoginUser,
        @RequestParam q: String,
    ): PlaylistsDto {
        return myPlaylistService.findMyPlaylistsByTitle(user.userId, q)
    }

    @ResponseBody
    @GetMapping("/playlists/search-all")
    fun searchAllPlaylists(
        @Login user: LoginUser,
        @RequestParam q: String,
        @PageableDefault(sort = ["updatedDate"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PlaylistsDto {
        return allPlaylistService.findAllPlaylistsByTitle(user, pageable, q)
    }

    @GetMapping("/")
    fun redirectPlaylistsView(): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.location = URI.create("/playlists")
        return ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY)
    }
}