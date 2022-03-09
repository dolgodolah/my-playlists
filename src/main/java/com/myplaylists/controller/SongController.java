package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Song;
import com.myplaylists.dto.*;
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
    public ResponseEntity<List<YoutubeForm>> youtubeSearch(@Login LoginUser user, @RequestParam String keyword) throws IOException, ParseException {
        return ResponseEntity.ok(youtubeService.search(keyword));
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
}
