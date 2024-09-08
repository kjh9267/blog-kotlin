package me.jun.core.guestbook.domain

import me.jun.core.guestbook.domain.exception.WriterMismatchException
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

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class PostTest {

    private lateinit var post: Post

    @Mock
    private lateinit var mockPostInfo: PostInfo

    @Mock
    private lateinit var mockWriter: Writer

    @BeforeEach
    fun setUp() {
        post = post().apply {
            this.postInfo = mockPostInfo
            this.writer = mockWriter
        }
    }

    @Test
    fun constructorTest() {
        val expected: Post = Post(
            postId = POST_ID,
            postInfo = postInfo(),
            writer = postWriter(),
            createdAt = POST_CREATED_AT,
            updatedAt = POST_UPDATED_AT
        )

        assertThat(post())
            .isEqualToIgnoringGivenFields(expected, "postInfo")
    }

    @Test
    fun updatePostInfoTest() {
        val expected: Post = post()

        given(mockPostInfo.updateTitle(any()))
            .willReturn(mockPostInfo)

        given(mockPostInfo.updateContent(any()))
            .willReturn(mockPostInfo)

        doNothing()
            .`when`(mockWriter)
            .validate(any())

        assertAll(
            {
                assertThat(post.updatePostInfo(POST_WRITER_ID, NEW_POST_TITLE, NEW_POST_CONTENT))
                    .isEqualToIgnoringGivenFields(expected, "writer", "postInfo")
            },
            {
                verify(mockWriter)
                    .validate(POST_WRITER_ID)
            }
        )
    }

    @Test
    fun invalidWriter_updatePostFailTest() {
        given(mockWriter.validate(any()))
            .willThrow(WriterMismatchException.of(POST_WRITER_ID.toString()))

        assertThrows(
            WriterMismatchException::class.java
        ) {
            post.updatePostInfo(
                writerId = 1L,
                newTitle = "new post title string",
                newContent = "new post content string")
        }
    }
}