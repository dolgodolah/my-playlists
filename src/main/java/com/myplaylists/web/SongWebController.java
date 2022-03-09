package com.myplaylists.web;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.dto.LoginUser;
import com.myplaylists.service.BookmarkService;
import com.myplaylists.service.PlaylistService;
import com.myplaylists.service.SongService;
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
    private final BookmarkService bookmarkService;

    @GetMapping("/playlist/{playlistId}/add")
    public String viewSongSearchForm(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, Model model) {
        Playlist playlist = playlistService.findPlaylist(playlistId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("songs", playlist.getSongs());

        return "playlist/addSong";
    }

    @GetMapping("/playlist/{playlistId}/{songId}")
    public String playSong(@Login LoginUser user, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId, Model model) {
        Playlist playlist = playlistService.findPlaylist(playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark", bookmarkService.checkBookmark(user.getId(), playlistId));

        return "playlist/playSong";
    }

    @GetMapping("/playlist/{playlistId}/{songId}/update")
    public String viewSongForm(@Login LoginUser user, Model model, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") Long songId) {
        Playlist playlist = playlistService.findPlaylist(playlistId);
        Song song = songService.getSong(songId);
        model.addAttribute("playlist", playlist);
        model.addAttribute("author", playlist.getUser().getNickname());
        model.addAttribute("nowSong", song);
        model.addAttribute("songs", playlist.getSongs());
        model.addAttribute("isBookmark",bookmarkService.checkBookmark(user.getId(), playlistId));

        return "playlist/updateSong";
    }
}
