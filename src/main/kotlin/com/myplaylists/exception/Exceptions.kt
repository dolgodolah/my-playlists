package com.myplaylists.exception

import java.lang.RuntimeException

private const val BAD_REQUEST = 400
private const val NOT_AUTHORIZED = 401
private const val NOT_FOUND = 404
private const val YOUTUBE_API = 450
private const val EXCEED_LIMIT = 460

open class ApiException(
    override val message: String,
    val statusCode: Int,
): RuntimeException(message)

class NotAuthorizedException(
    message: String = "로그인을 먼저 진행해주세요.",
): ApiException(message, NOT_AUTHORIZED)

class NotFoundException(
    message: String
): ApiException(message, NOT_FOUND)

class BadRequestException(
    message: String
): ApiException(message, BAD_REQUEST)

class ExceedLimitException(
    message: String
): ApiException(message, EXCEED_LIMIT)

class YoutubeApiException(
    message: String = "유튜브 검색에 실패했습니다. 잠시 후 다시 시도해보세요."
): ApiException(message, YOUTUBE_API)

