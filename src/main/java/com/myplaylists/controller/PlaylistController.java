package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.dto.PlaylistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.domain.Playlist;
import com.myplaylists.service.BookmarkService;
import com.myplaylists.service.PlaylistService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaylistController {
	
	private final PlaylistService playlistService;
	private final BookmarkService bookmarkService;

	@GetMapping("/my-playlists")
	public ResponseEntity<List<PlaylistResponseDto>> getMyPlaylists(@Login LoginUser user, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(playlistService.findMyPlaylists(pageable, user.getId()));
	}

	@GetMapping("/my-playlists-size")
	public ResponseEntity<Long> getMyPlaylistsSize(@Login LoginUser user) {
		return ResponseEntity.ok(playlistService.getMyPlaylistsSize(user.getId()));
	}
	
	@GetMapping("/all-playlists")
	public ResponseEntity<List<PlaylistResponseDto>> getAllPlaylists(@PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(playlistService.findAllPlaylists(pageable));
	}

	@PostMapping("/playlist")
	public ResponseEntity<PlaylistResponseDto> addPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser loginUser) {
		PlaylistResponseDto playlistResponseDto = playlistService.addPlaylist(loginUser, playlistRequestDto);
		return ResponseEntity.ok(playlistResponseDto);
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public String deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(playlistId);
		return "redirect:/mylist";
	}


}
