package com.myplaylists.dto.oauth

import com.google.gson.annotations.SerializedName
import com.myplaylists.domain.OauthType
import com.myplaylists.domain.User
import com.myplaylists.dto.BaseResponse

class OauthDto(
    val email: String,
    val name: String,
    val oauthType: OauthType
): BaseResponse() {
    fun toEntity(): User =
        User(
            email = email,
            name = name,
            nickname = name,
            oauthType = oauthType
        )
}

class KakaoOauthDto(
    @SerializedName("kakao_account")
    val kakaoAccount: KakaoAccount
) {
    fun toOauthDto(): OauthDto =
        OauthDto(
            email = kakaoAccount.email,
            name = kakaoAccount.profile.nickname,
            oauthType = OauthType.KAKAO
        )
}

data class KakaoAccount(
    val profile: KakaoProfile,
    val email: String,
) {

}

data class KakaoProfile(
    val nickname: String
) {

}

class GoogleOauthDto(
    val email: String,
    val name: String,
) {
    fun toOauthDto(): OauthDto {
        return OauthDto(
            email = email,
            name = name,
            oauthType = OauthType.GOOGLE
        )
    }
}