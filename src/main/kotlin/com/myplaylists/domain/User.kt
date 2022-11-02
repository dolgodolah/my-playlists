package com.myplaylists.domain

import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    var id: Long? = null,
    var name: String,
    var nickname: String,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    val oauthType: OauthType
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

enum class OauthType(
    val value: Int
) {
    NONE(0), KAKAO(1), GOOGLE(2)
}

fun Optional<User>.signUpOrLogin() {
    // TODO
}