package me.jun.core.guestbook.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class PostTest {

    @Test
    fun constructorTest() {
        val expected: Post = Post(
            postId = POST_ID,
            title = POST_TITLE,
            content = POST_CONTENT,
            writerId = ARTICLE_WRITER_ID,
            createdAt = POST_CREATED_AT,
            updatedAt = POST_UPDATED_AT
        )

        assertThat(post())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun updateTitleTest() {
        val expected: String = NEW_POST_TITLE

        assertThat(
            post().updateTitle("new post title string")
                .title
        )
            .isEqualTo(expected)
    }

    @Test
    fun updateContentTest() {
        val expected: String = NEW_POST_CONTENT

        assertThat(
            post().updateContent("new post content string")
                .content
        )
            .isEqualTo(expected)
    }
}