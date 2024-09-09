package me.jun.core.blog.domain

import me.jun.support.ARTICLE_ID
import me.jun.support.TAGGED_ARTICLE_ID
import me.jun.support.TAG_ID
import me.jun.support.taggedArticle
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

@Suppress("Deprecation")
class TaggedArticleTest {

    @Test
    fun constructorTest() {
        val expected: TaggedArticle = TaggedArticle(
            taggedArticleId = TAGGED_ARTICLE_ID,
            tagId = TAG_ID,
            articleId = ARTICLE_ID
        )

        assertThat(taggedArticle())
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun matchTest() {
        val expected: TaggedArticle = TaggedArticle(
            taggedArticleId = TAGGED_ARTICLE_ID,
            tagId = TAG_ID,
            articleId = ARTICLE_ID
        )

        val taggedArticle: TaggedArticle = taggedArticle().apply {
            this.tagId = null
            this.articleId = null
        }

        assertThat(taggedArticle.match(TAG_ID, ARTICLE_ID))
            .isEqualToComparingFieldByField(expected)
    }
}