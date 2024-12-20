package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Article
import me.jun.support.ARTICLE_ID
import me.jun.support.article
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import java.time.Instant.now

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ArticleRepositoryTest {

    @Autowired
    private lateinit var articleRepository: ArticleRepository;

    @Test
    fun findByArticleIdTest() {
        val expected: Article = article()

        articleRepository.save(
            article().apply {
                this.articleId = null
                this.createdAt = null
                this.updatedAt = null
            }
        )

        val article: Article = articleRepository.findByArticleId(ARTICLE_ID)!!

        assertAll(
            {
                assertThat(article)
                    .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt")
            },
            {
                assertThat(article.createdAt)
                    .isBeforeOrEqualTo(now())
            },
            {
                assertThat(article.updatedAt)
                    .isBeforeOrEqualTo(now())
            }
        )
    }

    @Test
    fun findByArticleIdFailTest() {
        assertThat(articleRepository.findByArticleId(ARTICLE_ID))
            .isNull();
    }

    @Test
    fun findAllByTest() {
        for (id in 1..20) {
            articleRepository.save(
                article().apply {
                    this.articleId = id.toLong()
                }
            )
        }

        val page: Page<Article> = articleRepository.findAllBy(PageRequest.of(1, 10))
        assertThat(page.numberOfElements).isEqualTo(10)
    }

    @Test
    fun findAllByFailTest() {
        for (id in 1..10) {
            articleRepository.save(
                article().apply {
                    this.articleId = id.toLong()
                }
            )
        }

        val page: Page<Article> = articleRepository.findAllBy(PageRequest.of(1, 10))
        assertThat(page.isEmpty).isTrue()
    }

    @Test
    fun findAllByCategoryIdTest() {
        for (id in 1..20) {
            articleRepository.save(
                article().apply {
                    this.articleId = id.toLong()
                    this.categoryId = id.toLong() % 2
                }
            )
        }

        val page: Page<Article> = articleRepository.findAllByCategoryId(1L, PageRequest.of(0, 20))
        assertThat(page.numberOfElements).isEqualTo(10)
    }

    @Test
    fun findAllByCategoryIdFailTest() {
        for (count in 1..10) {
            articleRepository.save(article())
        }

        assertThat(articleRepository.findAllByCategoryId(2L, PageRequest.of(0, 10)).numberOfElements)
            .isZero()
    }
}