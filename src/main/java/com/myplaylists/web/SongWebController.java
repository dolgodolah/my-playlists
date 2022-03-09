package com.myplaylists.web;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Song;
import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistDto;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.SongService;
import com.myplaylists.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class SongWebController {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final YoutubeService youtubeService;

    @GetMapping("/playlist/{playlistId}/add")
    public String viewSongSearchForm(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, Model model) {
        PlaylistDto playlist = playlistService.findPlaylist(user.getId(), playlistId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getAuthor());
        model.addAttribute("songs", playlist.getSongs());

        return "playlist/addSong";
    }

    @GetMapping("/playlist/{playlistId}/{songId}")
    public String playSong(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId, Model model) {
        PlaylistDto playlist = playlistService.findPlaylist(user.getId(), playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getAuthor());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark", playlist.isBookmark());

        return "playlist/playSong";
    }

    @GetMapping("/playlist/{playlistId}/{songId}/update")
    public String viewSongForm(@Login LoginUser user, Model model, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
        PlaylistDto playlist = playlistService.findPlaylist(user.getId(), playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getAuthor());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark", playlist.isBookmark());

        return "playlist/updateSong";
    }
}
