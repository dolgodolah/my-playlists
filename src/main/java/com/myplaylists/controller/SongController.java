package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Song;
import com.myplaylists.dto.*;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.service.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final YoutubeService youtubeService;

    @GetMapping("/youtube_search")
    public ResponseDto youtubeSearch(@Login LoginUser user, @RequestParam String keyword) throws IOException, ParseException {
        return ResponseDto.of(youtubeService.search(keyword));
    }

    @PostMapping("/song")
    public ResponseEntity<SongResponseDto> addSong(@RequestBody SongRequestDto songRequestDto) {
        Song song = songService.addSong(songRequestDto);
        return ResponseEntity.ok(SongResponseDto.of(song));
    }

    @PutMapping("/song/{songId}")
    public void updateSong(@Login LoginUser user, @RequestBody SongRequestDto songRequestDto, @PathVariable("songId") Long songId) {
        songService.updateSong(user, songRequestDto, songId);
    }

    @DeleteMapping("/song/{songId}")
    public void deleteSong(@Login LoginUser user, @PathVariable("songId") Long songId) {
        songService.deleteSong(user, songId);
    }

    @GetMapping("/songs")
    public ResponseEntity<SongsDto> getSongsByPlaylistId(@Login LoginUser user, @RequestParam Long playlistId) {
        List<Song> songs = songService.findSongsByPlaylistId(playlistId);
        return ResponseEntity.ok(SongsDto.of(songs));
    }
}
