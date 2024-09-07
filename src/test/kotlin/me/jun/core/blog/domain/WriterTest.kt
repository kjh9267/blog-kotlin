package me.jun.core.blog.domain

import me.jun.core.blog.domain.exception.WriterMismatchException
import me.jun.support.POST_WRITER_ID
import me.jun.support.writer
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@Suppress("Deprecation")
class WriterTest {

    @Test
    fun constructorTest() {
        val expected: Writer = Writer(value = POST_WRITER_ID)

        assertThat(Writer(POST_WRITER_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun validateTest() {
        assertDoesNotThrow {
            writer().validate(POST_WRITER_ID)
        }
    }

    @Test
    fun validateFailTest() {
        assertThrows(
            WriterMismatchException::class.java
        ) {
            writer().validate(0L)
        }
    }
}