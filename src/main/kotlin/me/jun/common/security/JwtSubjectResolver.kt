package me.jun.common.security

import me.jun.common.security.exception.InvalidTokenException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtSubjectResolver(
    private val jwtProvider: JwtProvider,
    private val memberIdExtractor: MemberIdExtractor
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(WriterId::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val token: String = webRequest.getHeader(AUTHORIZATION)
            ?: throw InvalidTokenException.of("null token")

        val email: String = jwtProvider.extractSubject(token)

        return memberIdExtractor.extractMemberId(email)
            ?: throw InvalidTokenException.of(token)
    }
}