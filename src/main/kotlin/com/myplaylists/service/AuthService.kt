package com.myplaylists.service

import com.myplaylists.client.GoogleClient
import com.myplaylists.client.KakaoClient
import com.myplaylists.domain.User
import com.myplaylists.dto.auth.LoginUser
import com.myplaylists.dto.oauth.OauthDto
import com.myplaylists.dto.oauth.OauthType
import com.myplaylists.exception.BadRequestException
import com.myplaylists.exception.SignupRequiredException
import com.myplaylists.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val googleClient: GoogleClient,
    private val kakaoClient: KakaoClient,
) {

    /**
     * 내플리스 로그인 처리
     */
    fun login(code: String, oauthType: OauthType): LoginUser {
        val oauthDto = when (oauthType) {
            OauthType.GOOGLE -> getGoogleOauthDto(code)
            OauthType.KAKAO -> getKakaoOauthDto(code)
            else -> throw BadRequestException()
        }

        return try {
            val user = authenticate(oauthDto)
            LoginUser(user)
        } catch (e: SignupRequiredException) {
            userService.signup(oauthDto)
        }
    }

    /**
     * 1. 존재하지 않는 이메일이면 회원가입 요구
     * 2. 존재하는 이메일이면 OAUTH 타입에 따라 처리
     *   2-1. 같은 OAUTH 타입이 이미 존재하면 최신 정보로 업데이트 후 로그인 처리
     *   2-2. 새로운 OAUTH 타입이라면 회원가입 요구
     */
    private fun authenticate(oauthDto: OauthDto): User {
        val users = userRepository.findAllByEmail(oauthDto.email)
        if (users.isNullOrEmpty()) {
            throw SignupRequiredException()
        }

        return users.firstOrNull { it.oauthType == oauthDto.oauthType }
            ?.let { it.updateName(oauthDto.name) }
            ?: throw SignupRequiredException()
    }

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    private fun getKakaoOauthDto(code: String): OauthDto =
        kakaoClient.getKakaoUserInfo(code).toOauthDto()

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 구글 로그인 정보로부터 가져온다.
     */
    private fun getGoogleOauthDto(code: String): OauthDto =
        googleClient.getGoogleUserInfo(code).toOauthDto()
}