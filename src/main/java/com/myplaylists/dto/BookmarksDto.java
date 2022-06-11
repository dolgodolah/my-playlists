package com.myplaylists.dto;

import com.myplaylists.domain.Bookmark;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class BookmarksDto extends BaseResponse {
    private List<Bookmark> bookmarks;
    private boolean isLast;

    public static BookmarksDto of(Page<Bookmark> bookmarks) {
        return BookmarksDto.builder()
                .bookmarks(bookmarks.toList())
                .isLast(bookmarks.isLast())
                .build();
    }
}
