package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.*;
import com.myplaylists.domain.Song;
import com.myplaylists.service.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final YoutubeService youtubeService;

    @GetMapping("/youtube_search")
    public ResponseEntity<List<YoutubeForm>> youtubeSearch(@Login LoginUser user, @RequestParam String keyword) throws IOException, ParseException {
        return ResponseEntity.ok(youtubeService.search(keyword));
    }

    @PostMapping("/song")
    public ResponseEntity<SongResponseDto> addSong(@RequestBody SongRequestDto songRequestDto) {
        return ResponseEntity.ok(songService.addSong(songRequestDto));
    }

    @PutMapping("/song/{songId}")
    public void updateSong(@RequestBody SongRequestDto songRequestDto, @PathVariable("songId") Long songId) {
        songService.updateSong(songId, songRequestDto);
    }

    @DeleteMapping("/song/{songId}")
    public void deleteSong(@PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
        songService.deleteSong(songId);
    }
}
