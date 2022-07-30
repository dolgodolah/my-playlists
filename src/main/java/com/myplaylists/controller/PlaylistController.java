package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.*;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.service.PlaylistService;


@RestController
@RequiredArgsConstructor
public class PlaylistController {
	
	private final PlaylistService playlistService;
	private final BookmarkService bookmarkService;

	@GetMapping("/my_playlists")
	public PlaylistsDto getMyPlaylists(@Login LoginUser user, @PageableDefault(sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return playlistService.findMyPlaylists(user.getUserId(), pageable);
	}

	@GetMapping("/all_playlists")
	public PlaylistsDto getAllPlaylists(@PageableDefault(sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return playlistService.findAllPlaylists(pageable);
	}

	@PostMapping("/playlist")
	public BaseResponse createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser user) {
		playlistService.createPlaylist(user.getUserId(), playlistRequestDto);
		return BaseResponse.ok();
	}

	@GetMapping("/playlist/{playlistId}")
	public BookmarkDto checkBookmarkPlaylist(@PathVariable("playlistId") Long playlistId, @Login LoginUser user) {
		return bookmarkService.checkBookmark(user.getUserId(), playlistId);
	}

	@DeleteMapping("/playlist/{playlistId}")
	public BaseResponse deletePlaylist(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(user.getUserId(), playlistId);
		return BaseResponse.ok();
	}

	@GetMapping("/bookmarks")
	public PlaylistsDto getBookmarkPlaylists(@Login LoginUser user, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		BookmarksDto bookmarks = BookmarksDto.of(bookmarkService.findByUserId(user.getUserId(), pageable));
		PlaylistsDto playlists = PlaylistsDto.Companion.of(bookmarks);
		return playlists;
	}

	@PostMapping("/bookmark/{playlistId}")
	public BaseResponse toggleBookmark(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		bookmarkService.toggleBookmark(user.getUserId(), playlistId);
		return BaseResponse.ok();
	}

	@GetMapping("/playlist/search")
	public PlaylistsDto searchMyPlaylists(@Login LoginUser user, @RequestParam String keyword, @PageableDefault(sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return playlistService.searchMyPlaylists(user.getUserId(), pageable, keyword);
	}

	@GetMapping("/playlist/search_all")
	public PlaylistsDto searchAllPlaylists(String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return playlistService.searchAllPlaylists(pageable, keyword);
	}
}
