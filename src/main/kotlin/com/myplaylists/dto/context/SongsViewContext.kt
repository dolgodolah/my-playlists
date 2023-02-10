package com.myplaylists.dto.context

import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.dto.SongResponseDto
import com.myplaylists.dto.ViewContext

data class SongsViewContext(
    val songs: List<SongResponseDto>,
    val currentPlaylist: PlaylistResponseDto,
    val isGuest: Boolean = false
) : ViewContext