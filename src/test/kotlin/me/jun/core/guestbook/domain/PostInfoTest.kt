package me.jun.core.guestbook.domain

import me.jun.support.NEW_POST_CONTENT
import me.jun.support.NEW_POST_TITLE
import me.jun.support.postInfo
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class PostInfoTest {

    @Test
    fun constructorTest() {
        val expected = postInfo()

        assertThat(
            PostInfo(
                title = "post title string",
                content = "post content string"
            )
        )
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun updateTitleTest() {
        val expected: PostInfo = postInfo().apply {
            this.title = NEW_POST_TITLE
        }

        assertThat(
            postInfo().updateTitle("new post title string")
        )
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun updateContentTest() {
        val expected: PostInfo = postInfo().apply {
            this.content = NEW_POST_CONTENT
        }

        assertThat(postInfo().updateContent("new post content string"))
            .isEqualToComparingFieldByField(expected)
    }
}