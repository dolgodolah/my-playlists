package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.PlaylistDto;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.dto.PlaylistsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.service.PlaylistService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaylistController {
	
	private final PlaylistService playlistService;

	@GetMapping("/my-playlists")
	public ResponseEntity<PlaylistsDto> getMyPlaylists(@Login LoginUser user, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(playlistService.findMyPlaylists(pageable, user.getId()));
	}

	@GetMapping("/all-playlists")
	public ResponseEntity<List<PlaylistDto>> getAllPlaylists(@PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam boolean visibility) {
		return ResponseEntity.ok(playlistService.findAllPlaylists(pageable, visibility));
	}

	@GetMapping("/all-playlists-size")
	public ResponseEntity<Long> getAllPlaylistsSize(@RequestParam boolean visibility) {
		return ResponseEntity.ok(playlistService.getAllPlaylistsSize(visibility));
	}

	@PostMapping("/playlist")
	public ResponseEntity<PlaylistDto> addPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser loginUser) {
		PlaylistDto playlistDto = playlistService.addPlaylist(loginUser, playlistRequestDto);
		return ResponseEntity.ok(playlistDto);
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public String deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(playlistId);
		return "redirect:/mylist";
	}


}
