package com.myplaylists.web;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.PlaylistsDto;
import com.myplaylists.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final PlaylistService playlistService;

    @GetMapping("/me")
    public String myInfo(@Login LoginUser user, Model model, @PageableDefault(size=6, sort="updatedDate", direction= Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("playlists", playlistService.findMyPlaylists(user.getUserId(), pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "user/myInfo";
    }
}
