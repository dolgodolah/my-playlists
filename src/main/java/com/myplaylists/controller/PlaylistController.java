package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Playlist;
import com.myplaylists.dto.*;
import com.myplaylists.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

	@GetMapping("/my-playlists")
	public ResponseEntity<PlaylistsDto> getMyPlaylists(@Login LoginUser user, @PageableDefault(size = 6) Pageable pageable) {
		return ResponseEntity.ok(playlistService.findMyPlaylists(pageable, user.getId()));
	}

	@GetMapping("/all-playlists")
	public ResponseEntity<PlaylistsDto> getAllPlaylists(@PageableDefault(size = 6) Pageable pageable) {
		return ResponseEntity.ok(playlistService.findAllPlaylists(pageable));
	}

	@PostMapping("/playlist")
	public ResponseEntity<PlaylistResponseDto> addPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser loginUser) {
		Playlist playlist = playlistService.addPlaylist(loginUser, playlistRequestDto);
		return ResponseEntity.ok(PlaylistResponseDto.of(playlist));
	}

	@GetMapping("/playlist/{playlistId}")
	public ResponseEntity<PlaylistResponseDto> getPlaylistDetail(@PathVariable("playlistId") Long playlistId, @Login LoginUser user) {
		Playlist playlist = playlistService.findPlaylist(playlistId);
		boolean isBookmark = bookmarkService.checkBookmark(user.getId(), playlistId);
		return ResponseEntity.ok(PlaylistResponseDto.of(playlist, isBookmark));
	}
	
	@DeleteMapping("/playlist/{playlistId}")
		playlistService.deletePlaylist(playlistId);
	}

	@GetMapping("/bookmark")
	public ResponseEntity<PlaylistsDto> getBookmarkPlaylists(@Login LoginUser user, @PageableDefault(size=6, sort="createdDate",direction= Sort.Direction.DESC) Pageable pageable) {
		BookmarksDto bookmarks = BookmarksDto.of(bookmarkService.findByUserId(user.getId(), pageable));
		return ResponseEntity.ok(PlaylistsDto.of(bookmarks));
	}

	@PostMapping("/bookmark")
	public void toggleBookmark(@Login LoginUser user, @PathVariable("playlistId") Long playlistId) {
		bookmarkService.toggleBookmark(user.getId(), playlistId);
	}

	@GetMapping("/playlist/search")
	public ResponseEntity<PlaylistsDto> searchMyPlaylists(@Login LoginUser user, String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Playlist> playlists = playlistService.searchMyPlaylists(pageable, keyword, user.getId());
		return ResponseEntity.ok(PlaylistsDto.of(playlists));
	}

		playlistService.toggleBookmark(user.getId(), playlistId);
	public ResponseEntity<PlaylistsDto> searchAllPlaylists(String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Playlist> playlists = playlistService.searchAll(pageable, keyword);
		return ResponseEntity.ok(PlaylistsDto.of(playlists));
	}
}
