package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Article
import me.jun.support.ARTICLE_ID
import me.jun.support.article
import me.jun.support.articleInfo
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
class ArticleRepositoryTest {

    @Autowired
    lateinit var articleRepository: ArticleRepository;

    @Test
    fun findByIdTest() {
        val expected: Article = article()

        articleRepository.save(
            article().apply {
                this.id = null
            }
        )

        assertThat(articleRepository.findById(ARTICLE_ID).get())
            .isEqualToComparingFieldByField(expected)
    }
}