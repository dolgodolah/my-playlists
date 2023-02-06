package com.myplaylists.dto.context

import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.dto.ViewContext

class PlaylistsViewContext(
    val category: String = "my-playlists",
    val playlists: List<PlaylistResponseDto>,
    val isGuest: Boolean = false
) : ViewContext

