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
	public ResponseDto getMyPlaylists(@Login LoginUser user, @PageableDefault(size = 6) Pageable pageable) {
		PlaylistsDto playlists = playlistService.findMyPlaylists(user.getUserId(), pageable);
		return ResponseDto.of(playlists);
	}

	@GetMapping("/all_playlists")
	public ResponseDto getAllPlaylists(@PageableDefault(size = 6) Pageable pageable) {
		PlaylistsDto playlists = playlistService.findAllPlaylists(pageable);
		return ResponseDto.of(playlists);
	}

	@PostMapping("/playlist")
	public ResponseDto createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser user) {
		playlistService.createPlaylist(user.getUserId(), playlistRequestDto);
		return ResponseDto.ok();
	}

	@GetMapping("/playlist/{playlistId}")
	public ResponseDto getPlaylistDetail(@PathVariable("playlistId") Long playlistId, @Login LoginUser user) {
		PlaylistResponseDto playlist = playlistService.findPlaylistById(playlistId);
		playlist.setBookmark(bookmarkService.checkBookmark(user.getUserId(), playlistId));
		return ResponseDto.of(playlist);
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public ResponseDto deletePlaylist(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(user.getUserId(), playlistId);
		return ResponseDto.ok();
	}

	@GetMapping("/bookmarks")
	public ResponseDto getBookmarkPlaylists(@Login LoginUser user, @PageableDefault(size=6, sort="createdDate",direction= Sort.Direction.DESC) Pageable pageable) {
		BookmarksDto bookmarks = BookmarksDto.of(bookmarkService.findByUserId(user.getUserId(), pageable));
		PlaylistsDto playlists = PlaylistsDto.of(bookmarks);
		return ResponseDto.of(playlists);
	}

	@PostMapping("/bookmark")
	public ResponseDto toggleBookmark(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		bookmarkService.toggleBookmark(user.getUserId(), playlistId);
		return ResponseDto.ok();
	}

	@GetMapping("/playlist/search")
	public ResponseDto searchMyPlaylists(@Login LoginUser user, String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaylistsDto playlists = playlistService.searchMyPlaylists(user.getUserId(), pageable, keyword);
		return ResponseDto.of(playlists);
	}

	@GetMapping("/playlist/search_all")
	public ResponseDto searchAllPlaylists(String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaylistsDto playlists = playlistService.searchAllPlaylists(pageable, keyword);
		return ResponseDto.of(playlists);
	}
}
