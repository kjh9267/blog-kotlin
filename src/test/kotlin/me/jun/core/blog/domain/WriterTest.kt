package me.jun.core.blog.domain

import me.jun.support.POST_WRITER_ID
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class WriterTest {

    @Test
    fun constructorTest() {
        val expected: Writer = Writer(value = POST_WRITER_ID)

        assertThat(Writer(POST_WRITER_ID))
            .isEqualToComparingFieldByField(expected)
    }
}