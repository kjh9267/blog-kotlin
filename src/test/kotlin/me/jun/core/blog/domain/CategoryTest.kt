package me.jun.core.blog.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class CategoryTest {

    @Test
    fun constructorTest() {
        val expected: Category = Category(
            categoryId = CATEGORY_ID,
            name = CATEGORY_NAME,
            mappedArticleCount = INITIAL_MAPPED_ARTICLE_COUNT
        )

        assertThat(category())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun incrementMappedArticleCountTest() {
        val expected: Category = category().apply {
            this.mappedArticleCount = 1L
        }

        assertThat(category().incrementMappedArticleCount())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun decrementMappedArticleCountTest() {
        val expected: Category = category().apply {
            this.mappedArticleCount = 0L
        }

        assertThat(
            category().apply {
                mappedArticleCount = MAPPED_ARTICLE_COUNT
            }
                .decrementMappedArticleCount()
        )
            .isEqualToComparingFieldByField(expected)
    }
}