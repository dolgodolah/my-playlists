package com.myplaylists.repository.cache

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myplaylists.domain.Playlist
import com.myplaylists.dto.PlaylistCacheDTO
import com.myplaylists.dto.PlaylistUpdateRequestDto
import com.myplaylists.exception.NotFoundException
import com.myplaylists.repository.PlaylistJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
open class PlaylistCacheRepository(
    private val jdbcRepository: PlaylistJpaRepository,
    private val redisJsonDao: RedisJsonDao,
) {

    companion object {
        private const val PLAYLISTS_BY_USER_ID_PREFIX = "PSU_"
        private const val PLAYLIST_BY_ID_PREFIX = "PP_"
        private val gson = Gson()
        private val listCacheType = object : TypeToken<List<PlaylistCacheDTO>>() {}.type
    }

    open fun create(playlist: Playlist, userId: Long) {
        evictCache(userId = userId)
        jdbcRepository.save(playlist)
    }

    open fun deletePlaylistCache(playlistId: Long, userId: Long) {
        evictCache(userId = userId, playlistId = playlistId)
        jdbcRepository.deleteById(playlistId)
    }

    open fun findById(playlistId: Long): PlaylistCacheDTO {
        val key = "$PLAYLIST_BY_ID_PREFIX$playlistId"

        return redisJsonDao.get(key)?.let { cache ->
            gson.fromJson(cache, PlaylistCacheDTO::class.java)
        } ?: run {
            val dbValue = jdbcRepository.findById(playlistId).orElseThrow { NotFoundException("not found playlist, playlistId=$playlistId") }
            val returnValue = dbValue.toCacheDTO()
            redisJsonDao.setIfAbsent(key, gson.toJson(returnValue))
            return returnValue
        }
    }

    open fun findAllByUserId(userId: Long): List<PlaylistCacheDTO> {
        val key = "$PLAYLISTS_BY_USER_ID_PREFIX$userId"

        return redisJsonDao.get(key)?.let { cache ->
            gson.fromJson(cache, listCacheType)
        } ?: run {
            val dbValue = jdbcRepository.findAllByUserId(userId)
            val returnValue = dbValue.map { it.toCacheDTO() }

            if (returnValue.isNotEmpty()) {
                redisJsonDao.setIfAbsent(key, gson.toJson(returnValue))
            }

            returnValue
        }
    }

    open fun update(playlistId: Long, playlistRequestDto: PlaylistUpdateRequestDto) {
        val now = LocalDateTime.now()

        // DB 갱신
        val playlist = jdbcRepository.findById(playlistId).orElseThrow { throw NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다.") }
        playlist.updateTitleAndDescription(playlistRequestDto)

        // 캐시 갱신
        val playlistCacheDTO = playlist.toCacheDTO(now)
        val playlistsCacheDTO = findAllByUserId(playlist.userId).map {
            if (it.playlistId == playlist.id) {
                playlistCacheDTO
            } else {
                it
            }
        }
        redisJsonDao.setIfPresent("$PLAYLIST_BY_ID_PREFIX${playlist.id!!}", gson.toJson(playlistCacheDTO))
        redisJsonDao.setIfPresent("$PLAYLISTS_BY_USER_ID_PREFIX${playlist.userId}", gson.toJson(playlistsCacheDTO))
    }

    private fun evictCache(userId: Long? = null, playlistId: Long? = null) {
        userId?.let { redisJsonDao.delete("$PLAYLISTS_BY_USER_ID_PREFIX$it") }
        playlistId?.let { redisJsonDao.delete("$PLAYLIST_BY_ID_PREFIX$it")  }
    }
}