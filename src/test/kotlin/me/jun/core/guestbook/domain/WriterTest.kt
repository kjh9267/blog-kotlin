package me.jun.core.guestbook.domain

import me.jun.core.guestbook.domain.exception.WriterMismatchException
import me.jun.support.POST_WRITER_ID
import me.jun.support.postWriter
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@Suppress("Deprecation")
class WriterTest {

    @Test
    fun constructorTest() {
        val expected: Writer = Writer(
            value = POST_WRITER_ID
        )

        assertThat(postWriter())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun validateTest() {
        assertDoesNotThrow {
            postWriter().validate(POST_WRITER_ID)
        }
    }

    @Test
    fun validateFailTest() {
        assertThrows(
            WriterMismatchException::class.java
        ) {
            postWriter().validate(0L)
        }
    }
}