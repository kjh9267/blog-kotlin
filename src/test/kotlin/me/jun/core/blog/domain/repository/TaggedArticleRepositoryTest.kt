package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.TaggedArticle
import me.jun.support.TAGGED_ARTICLE_ID
import me.jun.support.taggedArticle
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaggedArticleRepositoryTest {

    @Autowired
    private lateinit var taggedArticleRepository: TaggedArticleRepository

    @Test
    fun findByTaggedArticleIdTest() {
        val expected: TaggedArticle = taggedArticle()

        taggedArticleRepository.save(
            taggedArticle().apply {
                this.taggedArticleId = null
            }
        )

        assertThat(taggedArticleRepository.findByTaggedArticleId(TAGGED_ARTICLE_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun findByTaggedArticleFailTest() {
        assertThat(taggedArticleRepository.findByTaggedArticleId(TAGGED_ARTICLE_ID))
            .isNull()
    }
}