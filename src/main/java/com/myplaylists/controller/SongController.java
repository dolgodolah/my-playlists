package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.*;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final YoutubeService youtubeService;

    @GetMapping("/youtube_search")
    public ResponseDto youtubeSearch(@Login LoginUser user, @RequestParam String keyword) {
        return ResponseDto.of(youtubeService.search(keyword));
    }

    @GetMapping("/songs")
    public ResponseDto getSongsByPlaylistId(@Login LoginUser user, @RequestParam Long playlistId) {
        SongsDto songs = songService.findSongsByPlaylistId(playlistId);
        return ResponseDto.of(songs);
    }

    @PostMapping("/songs")
    public ResponseDto addSong(@Login LoginUser user, @RequestBody SongAddRequestDto songRequestDto) {
        songService.addSong(user, songRequestDto);
        return ResponseDto.ok();
    }

    @PutMapping("/songs/{songId}")
    public void updateSong(@Login LoginUser user, @PathVariable("songId") Long songId, @RequestBody SongUpdateRequestDto songRequestDto) {
        songService.updateSong(user, songId, songRequestDto);
    }

    @DeleteMapping("/songs/{songId}")
    public void deleteSong(@Login LoginUser user, @PathVariable("songId") Long songId) {
        songService.deleteSong(user, songId);
    }
}
