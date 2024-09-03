package me.jun.core.member.domain

import me.jun.core.member.domain.exception.WrongPasswordException
import me.jun.support.PASSWORD
import me.jun.support.password
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@Suppress("Deprecation")
class PasswordTest {

    @Test
    fun constructorTest() {
        val expected: Password = Password(PASSWORD)

        assertThat(password())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun validateTest() {
        val password: Password = password()

        assertDoesNotThrow {
            password.validate(PASSWORD)
        }
    }

    @Test
    fun validateFailTest() {
        val password: Password = password()

        assertThrows(
            WrongPasswordException::class.java
        ) {
            password.validate("wrong password")
        }
    }
}