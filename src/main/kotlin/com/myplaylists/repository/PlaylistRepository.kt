package com.myplaylists.repository

import com.myplaylists.domain.Playlist
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PlaylistRepository {
    fun findAllByUserId(userId: Long): List<Playlist>
    fun findAllByUserIdAndTitleContaining(userId: Long, title: String): List<Playlist>
    fun findByVisibility(pageable: Pageable, visibility: Boolean): Page<Playlist>
    fun findByTitleContaining(pageable: Pageable, title: String): Page<Playlist>
    fun findByVisibilityAndTitleContaining(pageable: Pageable, visibility: Boolean, title: String): Page<Playlist>
}