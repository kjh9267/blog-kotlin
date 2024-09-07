package me.jun.core.blog.domain

import me.jun.core.blog.domain.exception.WriterMismatchException
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class ArticleTest {

    private lateinit var article: Article

    @Mock
    private lateinit var mockWriter: Writer

    @Mock
    private lateinit var mockArticleInfo: ArticleInfo

    @BeforeEach
    fun setUp() {
        article = article().apply {
            this.writer = mockWriter
            this.articleInfo = mockArticleInfo
        }
    }

    @Test
    fun constructorTest() {
        val expected: Article = Article(
            articleId = ARTICLE_ID,
            categoryId = CATEGORY_ID,
            articleInfo = articleInfo(),
            writer = articleWriter(),
            createdAt = ARTICLE_CREATED_AT,
            updatedAt = ARTICLE_UPDATED_AT
        )

        assertThat(article)
            .isEqualToIgnoringGivenFields(expected, "writer", "articleInfo")
    }

    @Test
    fun updateArticleInfoTest() {
        given(mockArticleInfo.updateTitle(any()))
            .willReturn(mockArticleInfo)

        given(mockArticleInfo.updateContent(any()))
            .willReturn(mockArticleInfo)

        doNothing()
            .`when`(mockWriter)
            .validate(any())

        assertAll(
            {
                assertDoesNotThrow {
                    article.updateArticleInfo(
                        writerId = 1L,
                        newTitle = "new title string",
                        newContent = "new content string"
                    )
                }
            },
            {
                verify(mockWriter)
                    .validate(POST_WRITER_ID)
            }
        )
    }

    @Test
    fun writerMismatch_updateArticleInfoFailTest() {
        given(mockWriter.validate(any()))
            .willThrow(WriterMismatchException.of(ARTICLE_WRITER_ID.toString()))

        assertThrows(
            WriterMismatchException::class.java,
        ) {
            article.updateArticleInfo(
                writerId = 0L,
                newTitle = "new title string",
                newContent = "new content string"
            )
        }
    }
}