package me.jun.core.member.domain.repository

import me.jun.core.member.domain.Member
import me.jun.core.member.domain.Role
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

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
            Member(
                memberId = null,
                name = MEMBER_NAME,
                email = MEMBER_EMAIL,
                password = password(),
                role = Role.USER,
                createdAt = MEMBER_CREATED_AT,
                updatedAt = MEMBER_UPDATED_AT
            )
        )

        assertThat(memberRepository.findByMemberId(MEMBER_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun findByMemberIdFailTest() {
        assertThat(memberRepository.findByMemberId(0L))
            .isNull();
    }
}