package com.myplaylists.controller;

import com.myplaylists.domain.User;
import com.myplaylists.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @ResponseBody
    @PostMapping("/playlist/{playlistId}/bookmark")
    public void toggleBookmark(@PathVariable("playlistId") Long playlistId, User user) {
        bookmarkService.toggleBookmark(user.getId(), playlistId);
    }
}
