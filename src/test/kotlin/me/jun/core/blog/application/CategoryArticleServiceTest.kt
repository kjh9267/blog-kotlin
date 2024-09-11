package me.jun.core.blog.application

import me.jun.core.blog.application.dto.PagedArticleResponse
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.application.exception.CategoryNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.exception.WriterMismatchException
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.repository.CategoryRepository
import me.jun.core.blog.domain.service.CategoryMatchingService
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class CategoryArticleServiceTest {

    private lateinit var categoryArticleService: CategoryArticleService

    @Mock
    private lateinit var articleRepository: ArticleRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var categoryMatchingService: CategoryMatchingService

    @BeforeEach
    fun setUp() {
        categoryArticleService = CategoryArticleService(
            categoryRepository = categoryRepository,
            articleRepository = articleRepository,
            categoryMatchingService = categoryMatchingService
        )
    }

    @Test
    fun updateCategoryOfArticleTest() {
        val article: Article = article()
        val oldCategory: Category = category()
        val newCategory: Category = category().apply {
            this.name = NEW_CATEGORY_NAME
        }

        given(articleRepository.findByArticleId(any()))
            .willReturn(article)

        given(categoryRepository.findByCategoryId(any()))
            .willReturn(oldCategory)

        given(categoryRepository.findByName(any()))
            .willReturn(newCategory)

        doNothing()
            .`when`(categoryMatchingService)
            .changeCategory(any(), any(), any())

        assertAll(
            { assertThat(
                categoryArticleService.updateCategoryOfArticle(updateCategoryOfArticleRequest())
            )
                .isEqualToComparingFieldByField(categoryArticleResponse()) },
            { verify(categoryMatchingService)
                .changeCategory(
                    article = article,
                    newCategory = newCategory,
                    oldCategory = oldCategory
                )
            }
        )
    }

    @Test
    fun invalidWriter_updateCategoryOfArticleFailTest() {
        given(articleRepository.findByArticleId(any()))
            .willReturn(
                article().apply {
                    this.writer.value = 2L
                }
            )

        assertThrows(
            WriterMismatchException::class.java
        ) {
            categoryArticleService.updateCategoryOfArticle(updateCategoryOfArticleRequest())
        }
    }

    @Test
    fun noNewCategory_updateCategoryOfArticleTest() {
        val article: Article = article()
        val oldCategory: Category = category()
        val newCategory: Category = category().apply {
            this.name = NEW_CATEGORY_NAME
        }

        given(articleRepository.findByArticleId(any()))
            .willReturn(article)

        given(categoryRepository.findByCategoryId(any()))
            .willReturn(oldCategory)

        given(categoryRepository.findByName(any()))
            .willReturn(null)

        given(categoryRepository.save(any()))
            .willReturn(newCategory)

        doNothing()
            .`when`(categoryMatchingService)
            .changeCategory(any(), any(), any())

        assertAll(
            { assertThat(
                categoryArticleService.updateCategoryOfArticle(updateCategoryOfArticleRequest())
            )
                .isEqualToComparingFieldByField(categoryArticleResponse()) },
            { verify(categoryMatchingService)
                .changeCategory(
                    article = article,
                    newCategory = newCategory,
                    oldCategory = oldCategory
                )
            }
        )
    }

    @Test
    fun noArticle_updateCategoryOfArticleFailTest() {
        given(articleRepository.findByArticleId(any()))
            .willThrow(ArticleNotFoundException.of(ARTICLE_ID.toString()))

        assertThrows(
            ArticleNotFoundException::class.java
        ) {
            categoryArticleService.updateCategoryOfArticle(updateCategoryOfArticleRequest())
        }
    }

    @Test
    fun noOldCategory_updateCategoryOfArticleFailTest() {
        given(articleRepository.findByArticleId(any()))
            .willReturn(article())

        given(categoryRepository.findByCategoryId(any()))
            .willReturn(null)

        assertThrows(
            CategoryNotFoundException::class.java
        ) {
            categoryArticleService.updateCategoryOfArticle(updateCategoryOfArticleRequest())
        }
    }

    @Test
    fun retrievePagedCategoryArticlesTest() {
        val expected: PagedArticleResponse = pagedArticleResponse()

        given(categoryRepository.findByName(any()))
            .willReturn(category())

        given(articleRepository.findAllByCategoryId(any(), any()))
            .willReturn(pagedArticles())

        assertThat(categoryArticleService.retrievePagedCategoryArticles(
            categoryName = CATEGORY_NAME,
            pageable = PageRequest.of(0, 10))
        )
            .isEqualToComparingFieldByField(expected)
    }
}