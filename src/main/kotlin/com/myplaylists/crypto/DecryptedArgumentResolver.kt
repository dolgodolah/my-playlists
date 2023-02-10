package com.myplaylists.crypto

import com.myplaylists.exception.BadRequestException
import com.myplaylists.utils.CryptoUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.util.HtmlUtils
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@Component
class DecryptedArgumentResolver(
    @Value("\${playlist.secret}") private val secretKey: String
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(Decrypted::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val req = webRequest.nativeRequest as HttpServletRequest

        req.getParameter("p")?.let { p ->
            return CryptoUtils.decrypt(p, secretKey).toLong()
        } ?: throw BadRequestException()
    }
}