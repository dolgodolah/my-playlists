package com.myplaylists.exception

import java.lang.RuntimeException


object StatusCodes {
    const val BAD_REQUEST = 400
    const val NOT_AUTHORIZED = 401
    const val NOT_FOUND = 404
    const val YOUTUBE_API = 450
    const val EXCEED_LIMIT = 460
    const val SIGNUP_REQUIRED = 470
}

open class ApiException(
    override val message: String,
    val statusCode: Int,
): RuntimeException(message)

class NotAuthorizedException(
    message: String = "로그인을 먼저 진행해주세요.",
): ApiException(message, StatusCodes.NOT_AUTHORIZED)

class NotFoundException(
    message: String
): ApiException(message, StatusCodes.NOT_FOUND)

class BadRequestException(
    message: String = "올바르지 않은 요청입니다."
): ApiException(message, StatusCodes.BAD_REQUEST)

class ExceedLimitException(
    message: String
): ApiException(message, StatusCodes.EXCEED_LIMIT)

class YoutubeApiException(
    message: String = "유튜브 검색에 실패했습니다. 잠시 후 다시 시도해보세요."
): ApiException(message, StatusCodes.YOUTUBE_API)

class SignupRequiredException(
    message: String = "회원가입을 먼저 진행해주세요.",
): ApiException(message, StatusCodes.SIGNUP_REQUIRED)

class AuthRequiredException(): ApiException("", StatusCodes.NOT_AUTHORIZED);

