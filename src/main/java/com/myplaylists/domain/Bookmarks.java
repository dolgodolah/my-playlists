package com.myplaylists.domain;

import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Bookmarks {
    private Page<Bookmark> bookmarks;

    public Bookmarks(Page<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    /**
     * Use in Persistence Context (@Transactional)
=     */
    public List<Playlist> toPlaylists() {
        return this.bookmarks.stream()
                .map(bookmark -> bookmark.playlist)
                .collect(Collectors.toList());
    }
}
