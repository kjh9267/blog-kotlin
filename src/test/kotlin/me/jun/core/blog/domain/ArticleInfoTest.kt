package me.jun.core.blog.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class ArticleInfoTest {

    @Test
    fun constructorTest() {
        val expected: ArticleInfo = ArticleInfo(
            title = TITLE,
            content = CONTENT
        )

        assertThat(articleInfo())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun updateTitleTest() {
        val expected: ArticleInfo = ArticleInfo(
            title = NEW_TITLE,
            content = CONTENT
        )

        assertThat(articleInfo().updateTitle("new title string"))
            .isEqualToComparingFieldByField(expected);
    }

    @Test
    fun updateContentTest() {
        val expected: ArticleInfo = ArticleInfo(
            title = TITLE,
            content = NEW_CONTENT
        )

        assertThat(articleInfo().updateContent("new content string"))
            .isEqualToComparingFieldByField(expected)
    }
}