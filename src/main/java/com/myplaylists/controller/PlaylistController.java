package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.User;
import com.myplaylists.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
	public ResponseEntity<PlaylistDto> addPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser loginUser) {
		return ResponseEntity.ok(playlistService.addPlaylist(loginUser, playlistRequestDto));
	}

	@GetMapping("/playlist/{playlistId}")
	public ResponseEntity<PlaylistDto> getPlaylistDetail(@PathVariable("playlistId") Long playlistId, @Login LoginUser user) {
		return ResponseEntity.ok(playlistService.findPlaylist(user.getId(), playlistId));
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public void deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(playlistId);
	}

	@PostMapping("/playlist/{playlistId}/bookmark")
	public void toggleBookmark(@PathVariable("playlistId") Long playlistId, User user) {
		playlistService.toggleBookmark(user.getId(), playlistId);
	}
}
