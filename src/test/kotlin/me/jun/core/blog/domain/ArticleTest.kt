package me.jun.core.blog.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class ArticleTest {

    @Test
    fun constructorTest() {
        val expected: Article = Article(
            id = ARTICLE_ID,
            articleInfo = articleInfo(),
            writerId = WRITER_ID,
            createdAt = CREATED_AT,
            updatedAt = UPDATED_AT
        )

        assertThat(article())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun updateArticleInfoTest() {
        val expected: Article = Article(
            id = ARTICLE_ID,
            articleInfo = ArticleInfo(title = NEW_TITLE, content = NEW_CONTENT),
            writerId = WRITER_ID,
            createdAt = CREATED_AT,
            updatedAt = UPDATED_AT
        )

        assertThat(
            article().updateArticleInfo(
                newTitle = "new title string",
                newContent = "new content string"
            )
        )
            .isEqualToComparingFieldByField(expected)
    }
}