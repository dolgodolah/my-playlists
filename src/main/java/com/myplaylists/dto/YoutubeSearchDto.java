package com.myplaylists.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class YoutubeSearchDto extends BaseResponse {

    private List<Map<String, Object>> songs;

    public YoutubeSearchDto() {
        songs = new ArrayList<>();
    }

    public void add(Map<String, Object> song) {
        songs.add(song);
    }
}
