package me.jun.core.blog.application

import me.jun.core.blog.application.dto.ArticleResponse
import me.jun.core.blog.application.dto.PagedArticleResponse
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.service.CategoryMatchingService
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class ArticleServiceTest {

    private lateinit var articleService: ArticleService;

    @Mock
    private lateinit var articleRepository: ArticleRepository;

    @Mock
    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var categoryMatchingService: CategoryMatchingService

    @BeforeEach
    fun setUp() {
        articleService = ArticleService(
            articleRepository = articleRepository,
            categoryService = categoryService,
            categoryMatchingService = categoryMatchingService
        );
    }

    @Test
    fun createArticleTest() {
        val expected: ArticleResponse = articleResponse();
        val article: Article = article()
        val category: Category = category()

        given(categoryService.createCategoryOrElseGet(any()))
            .willReturn(category)

        doNothing()
            .`when`(categoryMatchingService)
            .matchCategory(any(), any())

        given(articleRepository.save(any()))
            .willReturn(article)

        assertAll(
            { assertThat(articleService.createArticle(createArticleRequest()))
                .isEqualToComparingFieldByField(expected) },
            { verify(categoryMatchingService)
                .matchCategory(
                    article = article.apply { this.articleId = null },
                    category = category
                )
            }
        )
    }

    @Test
    fun retrieveArticleTest() {
        val expected: ArticleResponse = articleResponse();

        given(articleRepository.findByArticleId(any()))
            .willReturn(article())

        assertThat(articleService.retrieveArticle(retrieveArticleRequest()))
            .isEqualToComparingFieldByField(expected);
    }

    @Test
    fun noArticle_retrieveArticleFailTest() {
        given(articleRepository.findByArticleId(any()))
            .willReturn(null)

        assertThrows(
            ArticleNotFoundException::class.java
        ) { articleService.retrieveArticle(retrieveArticleRequest()) }
    }

    @Test
    fun updateArticleTest() {
        val expected: ArticleResponse = updatedArticleResponse();

        given(articleRepository.findByArticleId(any()))
            .willReturn(updatedArticle())

        assertThat(articleService.updateArticle(updateArticleRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noArticle_updateArticleFailTest() {
        given(articleRepository.findByArticleId(any()))
            .willReturn(null)

        assertThrows(
            ArticleNotFoundException::class.java
        ) { articleService.updateArticle(updateArticleRequest()) }
    }

    @Test
    fun deleteArticleTest() {
        doNothing()
            .`when`(articleRepository)
            .deleteById(any())

        articleService.deleteArticle(deleteArticleRequest())

        verify(articleRepository)
            .deleteById(ARTICLE_ID)
    }

    @Test
    fun retrievePagedArticlesTest() {
        val expected: PagedArticleResponse = pagedArticleResponse()

        given(articleRepository.findAllBy(any()))
            .willReturn(pagedArticles())

        assertThat(articleService.retrievePagedArticles(PageRequest.of(0, 10)))
            .isEqualToComparingFieldByField(expected)
    }
}