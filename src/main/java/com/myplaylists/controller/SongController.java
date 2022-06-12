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
    private final YoutubeService youtubeService;

    @GetMapping("/youtube_search")
    public YoutubeSearchDto youtubeSearch(@Login LoginUser user, @RequestParam String keyword) {
        return youtubeService.search(keyword);
    }

    @GetMapping("/songs")
    public SongsDto getSongsByPlaylistId(@Login LoginUser user, @RequestParam Long playlistId) {
        return songService.findSongsByPlaylistId(playlistId);
    }

    @PostMapping("/songs")
    public BaseResponse addSong(@Login LoginUser user, @RequestBody SongAddRequestDto songRequestDto) {
        songService.addSong(user, songRequestDto);
        return BaseResponse.ok();
    }

    @PutMapping("/songs/{songId}")
    public BaseResponse updateSong(@Login LoginUser user, @PathVariable("songId") Long songId, @RequestBody SongUpdateRequestDto songRequestDto) {
        songService.updateSong(user, songId, songRequestDto);
        return BaseResponse.ok();
    }

    @DeleteMapping("/songs/{songId}")
    public BaseResponse deleteSong(@Login LoginUser user, @PathVariable("songId") Long songId) {
        songService.deleteSong(user, songId);
        return BaseResponse.ok();
    }

    @GetMapping("/songs/search")
    public SongsDto searchSongs(@Login LoginUser user, @RequestParam Long playlistId, @RequestParam String keyword) {
        return songService.searchSongs(playlistId, keyword);
    }
}
