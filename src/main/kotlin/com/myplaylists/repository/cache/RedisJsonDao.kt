package com.myplaylists.repository.cache

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

typealias Json = String

@Component
class RedisJsonDao(
    private val redisTemplate: RedisTemplate<String, Json>
) {

    fun get(key: String): Json? {
        return redisTemplate.opsForValue().get(key)
    }

    fun setIfAbsent(key: String, value: Json) {
        redisTemplate.opsForValue().setIfAbsent(key, value)
    }

    fun setIfPresent(key: String, value: Json) {
        redisTemplate.opsForValue().setIfPresent(key, value)
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}