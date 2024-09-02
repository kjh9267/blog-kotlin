package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Article
import me.jun.support.ARTICLE_ID
import me.jun.support.article
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles

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
            }
        )

        assertThat(articleRepository.findByArticleId(ARTICLE_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun findByArticleIdFailTest() {
        assertThat(articleRepository.findByArticleId(ARTICLE_ID))
            .isNull();
    }
}