package me.jun.common.security

import me.jun.common.security.exception.InvalidTokenException
import me.jun.support.EMAIL
import me.jun.support.JWT_KEY
import me.jun.support.TOKEN
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JwtProviderTest {

    private lateinit var jwtProvider: JwtProvider

    @BeforeEach
    fun setUp() {
        jwtProvider = JwtProvider(JWT_KEY)
    }

    @Test
    fun createTokenTest() {
        assertThat(jwtProvider.createToken(EMAIL))
            .isEqualTo(TOKEN)
    }

    @Test
    fun extractSubjectTest() {
        assertThat(jwtProvider.extractSubject(TOKEN))
            .isEqualTo(EMAIL)
    }

    @Test
    fun extractSubjectFailTest() {
        assertThrows(
            InvalidTokenException::class.java,
        ) { jwtProvider.extractSubject("wrong token") }
    }
}