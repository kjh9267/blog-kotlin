package me.jun.core.member.application

import me.jun.common.security.JwtProvider
import me.jun.core.member.application.dto.LoginRequest
import me.jun.core.member.application.dto.MemberResponse
import me.jun.core.member.application.dto.TokenResponse
import me.jun.core.member.application.exception.MemberNotFoundException
import me.jun.core.member.domain.Password
import me.jun.core.member.domain.exception.WrongPasswordException
import me.jun.core.member.domain.repository.MemberRepository
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class MemberServiceTest {

    private lateinit var memberService: MemberService

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Mock
    private lateinit var jwtProvider: JwtProvider

    @BeforeEach
    fun setUp() {
        memberService = MemberService(
            memberRepository,
            jwtProvider
        )
    }

    @Test
    fun registerTest() {
        val expected: MemberResponse = memberResponse()

        given(memberRepository.save(any()))
            .willReturn(user())

        assertThat(memberService.register(registerRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun loginTest() {
        val expected: TokenResponse = tokenResponse()

        given(memberRepository.findByEmail(any()))
            .willReturn(user())

        given(jwtProvider.createToken(any()))
            .willReturn(TOKEN)

        assertThat(memberService.login(loginRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noMember_loginFailTest() {
        given(memberRepository.findByEmail(any()))
            .willReturn(null)

        assertThrows(
            MemberNotFoundException::class.java,
        ) {
            memberService.login(loginRequest())
        }
    }

    @Test
    fun invalidPassword_loginFailTest() {
        given(memberRepository.findByEmail(any()))
            .willReturn(user())

        val request: LoginRequest = loginRequest().apply {
            this.password = "wrong password"
        }

        assertThrows(
            WrongPasswordException::class.java,
        ) {
            memberService.login(request)
        }
    }
}