package me.jun.core.blog.application

import me.jun.core.blog.application.dto.ArticleResponse
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.support.article
import me.jun.support.articleResponse
import me.jun.support.createArticleRequest
import me.jun.support.retrieveArticleRequest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class ArticleServiceTest {

    private lateinit var articleService: ArticleService;

    @Mock
    private lateinit var articleRepository: ArticleRepository;

    @BeforeEach
    fun setUp() {
        articleService = ArticleService(articleRepository);
    }

    @Test
    fun createArticleTest() {
        val expected: ArticleResponse = articleResponse();

        given(articleRepository.save(any()))
            .willReturn(article())

        assertThat(articleService.createArticle(createArticleRequest()))
            .isEqualToComparingFieldByField(expected)
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
}