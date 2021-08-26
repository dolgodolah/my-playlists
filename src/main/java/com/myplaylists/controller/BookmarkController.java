package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.LoginUser;
import com.myplaylists.domain.User;
import com.myplaylists.service.BookmarkService;
import com.myplaylists.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final UserService userService;

    @ResponseBody
    @PostMapping("/playlist/{playlistId}/bookmark")
    public void toggleBookmark(@PathVariable("playlistId") Long playlistId, User user) {
        bookmarkService.toggleBookmark(user.getId(), playlistId);
    }

    @GetMapping("/bookmark")
    public String bookmark(@Login LoginUser user, @PageableDefault(size=6, sort="createdAt",direction= Sort.Direction.DESC) Pageable pageable, Model model) {
        User loginUser = userService.getUser(user.getId());
        model.addAttribute("playlists", loginUser.getBookmarks());

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "playlist/bookmark";
    }
}
