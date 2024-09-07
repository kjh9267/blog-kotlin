package me.jun.core.guestbook.domain

import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class PostTest {

    private lateinit var post: Post

    @Mock
    private lateinit var mockPostInfo: PostInfo

    @BeforeEach
    fun setUp() {
        post = post().apply {
            this.postInfo = mockPostInfo
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

        assertThat(post.updatePostInfo(NEW_POST_TITLE, NEW_POST_CONTENT))
            .isEqualToIgnoringGivenFields(expected, "postInfo")
    }
}