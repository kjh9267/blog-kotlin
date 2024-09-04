package me.jun.common.security

import me.jun.core.member.application.MemberService
import me.jun.support.EMAIL
import me.jun.support.MEMBER_ID
import me.jun.support.memberResponse
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MemberIdExtractorTest {

    private lateinit var memberIdExtractor: MemberIdExtractor

    @Mock
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        memberIdExtractor = MemberIdExtractor(memberService)
    }

    @Test
    fun extractMemberIdTest() {
        given(memberService.retrieveMember(any()))
            .willReturn(memberResponse())

        assertThat(memberIdExtractor.extractMemberId(EMAIL))
            .isEqualTo(MEMBER_ID)
    }
}