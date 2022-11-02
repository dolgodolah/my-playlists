package com.myplaylists.domain

import com.myplaylists.dto.UserDto
import com.myplaylists.dto.oauth.OauthDto
import com.myplaylists.dto.oauth.OauthType
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

    companion object {
        fun of(oauthDto: OauthDto): User =
            User(
                email = oauthDto.email,
                name = oauthDto.name,
                nickname = oauthDto.name,
                oauthType = oauthDto.oauthType
            )
    }

    fun updateName(name: String): User {
        this.name = name
        return this
    }

    fun updateNickname(nickname: String): User {
        this.nickname = nickname
        return this
    }

    fun toDTO(): UserDto = UserDto(this.email, this.name, this.nickname)
}


