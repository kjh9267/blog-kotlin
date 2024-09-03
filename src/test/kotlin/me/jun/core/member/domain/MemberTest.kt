package me.jun.core.member.domain

import me.jun.core.member.domain.exception.WrongPasswordException
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@Suppress("Deprecation")
class MemberTest {

    @Test
    fun constructorTest() {
        val expected: Member = Member(
            memberId = MEMBER_ID,
            name = MEMBER_NAME,
            email = MEMBER_EMAIL,
            password = password(),
            role = Role.ADMIN,
            createdAt = MEMBER_CREATED_AT,
            updatedAt = MEMBER_UPDATED_AT
        )

        assertThat(admin())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun validatePasswordTest() {
        val user: Member = user()

        assertDoesNotThrow {
            user.validatePassword(PASSWORD)
        }
    }

    @Test
    fun validatePasswordFailTest() {
        val user: Member = user()

        assertThrows(
            WrongPasswordException::class.java
        ) {
            user.validatePassword("wrong password")
        }
    }
}