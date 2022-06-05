package com.myplaylists.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class YoutubeDto {

    private List<Map<String, Object>> songs;

    public YoutubeDto() {
        songs = new ArrayList<>();
    }

    public void add(Map<String, Object> song) {
        songs.add(song);
    }
}
