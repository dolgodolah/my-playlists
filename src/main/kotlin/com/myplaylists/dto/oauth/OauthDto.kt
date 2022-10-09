package com.myplaylists.dto.oauth

import com.google.gson.annotations.SerializedName
import com.myplaylists.domain.User


class KakaoOauthDto(
    @SerializedName("kakao_account")
    val kakaoAccount: KakaoAccount
) {
}

data class KakaoAccount(
    val profile: KakaoProfile,
    val email: String,
) {

    fun toEntity(): User {
        return User(
            name = profile.nickname,
            nickname = profile.nickname,
            email = email
        )
    }
}

data class KakaoProfile(
    val nickname: String
) {

}