package com.myplaylists.dto.context

import com.myplaylists.dto.PlaylistResponseDto
import com.myplaylists.dto.ViewContext

class MeViewContext(
    val name: String,
    val nickname: String,
    val email: String,
    val playlists: List<PlaylistResponseDto>,
) : ViewContext