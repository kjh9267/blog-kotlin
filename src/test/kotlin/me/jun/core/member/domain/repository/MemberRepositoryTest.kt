package me.jun.core.member.domain.repository

import me.jun.core.member.domain.Member
import me.jun.support.EMAIL
import me.jun.support.MEMBER_ID
import me.jun.support.user
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.time.Instant.now

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberRepositoryTest {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun findByMemberIdTest() {
        val expected: Member = user()

        memberRepository.save(
            user().apply {
                this.memberId = null
                this.createdAt = null
                this.updatedAt = null
            }
        )

        val member: Member = memberRepository.findByMemberId(MEMBER_ID)!!

        assertAll(
            {
                assertThat(member)
                    .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt")
            },
            {
                assertThat(member.createdAt)
                    .isBeforeOrEqualTo(now())
            },
            {
                assertThat(member.updatedAt)
                    .isBeforeOrEqualTo(now())
            }
        )
    }

    @Test
    fun findByMemberIdFailTest() {
        assertThat(memberRepository.findByMemberId(0L))
            .isNull();
    }

    @Test
    fun findByEmailTest() {
        val expected: Member = user()

        memberRepository.save(
            user().apply {
                this.memberId = null
                this.createdAt = null
                this.updatedAt = null
            }
        )

        val member: Member = memberRepository.findByEmail(EMAIL)!!

        assertAll(
            {
                assertThat(member)
                    .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt")
            },
            {
                assertThat(member.createdAt)
                    .isBeforeOrEqualTo(now())
            },
            {
                assertThat(member.updatedAt)
                    .isBeforeOrEqualTo(now())
            }
        )
    }

    @Test
    fun noMember_findByEmailFailTest() {
        assertThat(memberRepository.findByEmail(EMAIL))
            .isNull()
    }
}