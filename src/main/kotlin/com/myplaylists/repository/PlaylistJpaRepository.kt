package com.myplaylists.repository

import com.myplaylists.domain.Playlist
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaylistJpaRepository : JpaRepository<Playlist, Long>, PlaylistRepository {
    override fun findAllByUserId(userId: Long): List<Playlist>
    override fun findAllByUserIdAndTitleContaining(userId: Long, title: String): List<Playlist>
    override fun findByVisibility(pageable: Pageable, visibility: Boolean): Page<Playlist>
    override fun findByTitleContaining(pageable: Pageable, title: String): Page<Playlist>
    override fun findByVisibilityAndTitleContaining(pageable: Pageable, visibility: Boolean, title: String): Page<Playlist>

}