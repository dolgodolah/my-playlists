package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.*;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.service.PlaylistService;


@RestController
@RequiredArgsConstructor
public class PlaylistController {
	
	private final PlaylistService playlistService;
	private final BookmarkService bookmarkService;

	@GetMapping("/my_playlists")
	public ResponseEntity<PlaylistsDto> getMyPlaylists(@Login LoginUser user, @PageableDefault(size = 6) Pageable pageable) {
		System.out.println(user.getUserId());
		PlaylistsDto playlists = playlistService.findMyPlaylists(user.getUserId(), pageable);
		return ResponseEntity.ok(playlists);
	}

	@GetMapping("/all_playlists")
	public ResponseEntity<PlaylistsDto> getAllPlaylists(@PageableDefault(size = 6) Pageable pageable) {
		PlaylistsDto playlists = playlistService.findAllPlaylists(pageable);
		return ResponseEntity.ok(playlists);
	}

	@PostMapping("/playlist")
	public ResponseEntity createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser user) {
		playlistService.createPlaylist(user.getUserId(), playlistRequestDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/playlist/{playlistId}")
	public ResponseEntity<PlaylistResponseDto> getPlaylistDetail(@PathVariable("playlistId") Long playlistId, @Login LoginUser user) {
		PlaylistResponseDto playlist = playlistService.findPlaylistById(playlistId);
		playlist.setBookmark(bookmarkService.checkBookmark(user.getUserId(), playlistId));
		return ResponseEntity.ok(playlist);
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public ResponseEntity deletePlaylist(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(user.getUserId(), playlistId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/bookmarks")
	public ResponseEntity<PlaylistsDto> getBookmarkPlaylists(@Login LoginUser user, @PageableDefault(size=6, sort="createdDate",direction= Sort.Direction.DESC) Pageable pageable) {
		BookmarksDto bookmarks = BookmarksDto.of(bookmarkService.findByUserId(user.getUserId(), pageable));
		return ResponseEntity.ok(PlaylistsDto.of(bookmarks));
	}

	@PostMapping("/bookmark")
	public ResponseEntity toggleBookmark(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		bookmarkService.toggleBookmark(user.getUserId(), playlistId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/playlist/search")
	public ResponseEntity<PlaylistsDto> searchMyPlaylists(@Login LoginUser user, String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaylistsDto playlists = playlistService.searchMyPlaylists(user.getUserId(), pageable, keyword);
		return ResponseEntity.ok(playlists);
	}

	@GetMapping("/playlist/search_all")
	public ResponseEntity<PlaylistsDto> searchAllPlaylists(String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaylistsDto playlists = playlistService.searchAllPlaylists(pageable, keyword);
		return ResponseEntity.ok(playlists);
	}
}
