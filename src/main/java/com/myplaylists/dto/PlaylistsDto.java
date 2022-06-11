package com.myplaylists.dto;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.domain.Playlist;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PlaylistsDto extends BaseResponse {

    private List<PlaylistResponseDto> playlists;
    private boolean isLast;

    /**
     * 플레이리스트 목록을 업데이트 최신순으로 정렬하여 `DTO`로 반환
     */
    public static PlaylistsDto of(Page<Playlist> playlists) {
        List<PlaylistResponseDto> playlistDtoList = playlists.stream()
                .map(PlaylistResponseDto::of)
                .sorted(Comparator.comparing(PlaylistResponseDto::getUpdatedDate).reversed())
                .collect(Collectors.toList());

        return PlaylistsDto.builder()
                .playlists(playlistDtoList)
                .isLast(playlists.isLast())
                .build();
    }

    public static PlaylistsDto of(BookmarksDto bookmarksDto) {
        List<PlaylistResponseDto> playlistResponseDtoList = bookmarksDto.getBookmarks().stream()
                .map(Bookmark::getPlaylist)
                .map(PlaylistResponseDto::of)
                .collect(Collectors.toList());

        return PlaylistsDto.builder()
                .playlists(playlistResponseDtoList)
                .isLast(bookmarksDto.isLast())
                .build();
    }
}
