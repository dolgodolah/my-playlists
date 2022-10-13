package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.*;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/youtube_search")
    public YoutubeDto searchYoutube(@Login LoginUser user, @RequestParam String keyword) {
        return songService.searchYoutube(keyword);
    }

    @GetMapping("/songs")
    public SongsDto getSongsByPlaylistId(@Login LoginUser user, @RequestParam Long playlistId) {
        return songService.findSongsByPlaylistId(playlistId);
    }

    @PostMapping("/songs")
    public void addSong(@Login LoginUser user, @RequestBody SongAddRequestDto songRequestDto) {
        songService.addSong(user, songRequestDto);
    }

    @PutMapping("/songs/{songId}")
    public void updateSong(@Login LoginUser user, @PathVariable("songId") Long songId, @RequestBody SongUpdateRequestDto songRequestDto) {
        songService.updateSong(user, songId, songRequestDto);
    }

    @DeleteMapping("/songs/{songId}")
    public void deleteSong(@Login LoginUser user, @PathVariable("songId") Long songId) {
        songService.deleteSong(user, songId);
    }

    @GetMapping("/songs/search")
    public SongsDto searchSongs(@Login LoginUser user, @RequestParam Long playlistId, @RequestParam String keyword) {
        return songService.searchSongs(playlistId, keyword);
    }
}
