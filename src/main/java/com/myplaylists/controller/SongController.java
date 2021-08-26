package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.LoginUser;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.dto.YoutubeForm;
import com.myplaylists.service.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SongController {

    private final PlaylistService playlistService;
    private final UserService userService;
    private final SongService songService;
    private final BookmarkService bookmarkService;
    private final YoutubeService youtubeService;


    @GetMapping("/playlist/{playlistId}/add")
    public String viewSongSearchForm(@PathVariable("playlistId") Long playlistId, Model model) {
        Playlist playlist = playlistService.getPlaylist(playlistId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("songs", playlist.getSongs());

        return "playlist/addSong";
    }

    @GetMapping("/playlist/{playlistId}/youtube_search")
    public String youtubeSearch(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, String keyword, Model model) throws IOException, ParseException {
        Playlist playlist = playlistService.getPlaylist(playlistId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("songs", playlist.getSongs());

        List<YoutubeForm> songs = youtubeService.search(keyword);
        model.addAttribute("result", songs);

        return "playlist/addSong";
    }

    @PostMapping("/playlist/{playlistId}/{title}/{videoId}")
    public String addSong(@PathVariable("playlistId") Long playlistId, @PathVariable("title") String title, @PathVariable("videoId") String videoId) {
        Playlist playlist = playlistService.getPlaylist(playlistId);
        songService.addSong(playlist, title, videoId);
        return "redirect:/playlist/{playlistId}";
    }

    @GetMapping("/playlist/{playlistId}/{songId}")
    public String playSong(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId, Model model) {
        Playlist playlist = playlistService.getPlaylist(playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark", bookmarkService.validateBookmark(user.getId(), playlistId).isPresent());

        return "playlist/playSong";
    }

    @GetMapping("/playlist/{playlistId}/{songId}/update")
    public String viewSongForm(@Login LoginUser user, Model model, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
        Playlist playlist = playlistService.getPlaylist(playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark",bookmarkService.validateBookmark(user.getId(), playlistId).isPresent());

        return "playlist/updateSong";
    }

    @PutMapping("/playlist/{playlistId}/{songId}/update")
    public String updateSong(String title, String description, @PathVariable("songId") Long songId) {
        songService.updateSong(songId, title, description);
        return "redirect:/playlist/{playlistId}/{songId}";
    }

    @DeleteMapping("/playlist/{playlistId}/{songId}")
    public String deleteSong(@PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
        songService.deleteSong(songId);
        return "redirect:/playlist/{playlistId}";
    }
}
