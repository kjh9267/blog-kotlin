package me.jun.core.guestbook.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class PostCountTest {

    @Test
    fun constructorTest() {
        val expected: PostCount = postCount()

        assertThat(
            PostCount(
                postCountId = POST_COUNT_ID,
                value = POST_COUNT_VALUE,
                postId = POST_ID,
                version = VERSION
            )
        )
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun incrementTest() {
        val expected: PostCount = postCount().apply {
            this.value = 1L
        }

        assertThat(postCount().increment())
            .isEqualToComparingFieldByField(expected)
    }
}
