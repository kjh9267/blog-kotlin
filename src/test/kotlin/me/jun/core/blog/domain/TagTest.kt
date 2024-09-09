package me.jun.core.blog.domain

import me.jun.support.TAG_ID
import me.jun.support.TAG_NAME
import me.jun.support.tag
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class TagTest {

    @Test
    fun constructorTest() {
        val expected: Tag = Tag(
            tagId = TAG_ID,
            name = TAG_NAME
        )

        assertThat(tag())
            .isEqualToComparingFieldByField(expected)
    }
}