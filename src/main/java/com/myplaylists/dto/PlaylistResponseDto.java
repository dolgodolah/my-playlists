package com.myplaylists.dto;

import com.myplaylists.domain.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponseDto {
    private Long playlistId;
    private String title;
    private String description;
    private LocalDateTime updatedDate;
    private boolean visibility;

    public static PlaylistResponseDto of(Playlist playlist) {
        return PlaylistResponseDto.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .updatedDate(playlist.getUpdatedDate())
                .visibility(playlist.isVisibility())
                .build();
    }
}
