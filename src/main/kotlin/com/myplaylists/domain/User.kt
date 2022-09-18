package com.myplaylists.domain

import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    val userId: Long? = null,

    var name: String,
    var nickname: String,

    @Column(nullable = false)
    var email: String,
): BaseTime() {
    fun updateName(name: String): User {
        this.name = name
        return this
    }

    fun updateNickname(nickname: String): User {
        this.nickname = nickname
        return this
    }
}