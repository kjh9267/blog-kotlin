package me.jun.core.blog.domain.service

import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import me.jun.support.MAPPED_ARTICLE_COUNT
import me.jun.support.NEW_CATEGORY_NAME
import me.jun.support.article
import me.jun.support.category
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class CategoryMatchingServiceTest {

    private lateinit var categoryMatchingService: CategoryMatchingService

    @BeforeEach
    fun setUp() {
        categoryMatchingService = CategoryMatchingService()
    }

    @Test
    fun matchCategoryTest() {
        val expected: Long = 1L
        val category: Category = category()

        categoryMatchingService.matchCategory(
            article = article(),
            category = category
        )

        assertThat(category.mappedArticleCount)
            .isEqualTo(expected)
    }

    @Test
    fun changeCategoryTest() {
        val expected: String = NEW_CATEGORY_NAME
        val article: Article = article()
        val oldCategory: Category = category().apply {
            this.mappedArticleCount = MAPPED_ARTICLE_COUNT
        }
        val newCategory: Category = category().apply {
            this.categoryId = 2L
            this.name = "new category name string"
        }

        categoryMatchingService.changeCategory(article, newCategory, oldCategory)

        assertAll(
            { assertThat(article.categoryId).isEqualTo(2L) },
            { assertThat(newCategory.mappedArticleCount).isEqualTo(1L) },
            { assertThat(oldCategory.mappedArticleCount).isEqualTo(0L) }
        )
    }
}