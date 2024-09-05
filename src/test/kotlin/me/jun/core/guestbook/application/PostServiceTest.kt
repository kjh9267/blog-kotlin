package me.jun.core.guestbook.application

import me.jun.core.guestbook.application.dto.PostResponse
import me.jun.core.guestbook.application.exception.PostNotFoundException
import me.jun.core.guestbook.domain.repository.PostRepository
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class PostServiceTest {

    private lateinit var postService: PostService

    @Mock
    private lateinit var postRepository: PostRepository

    @BeforeEach
    fun setUp() {
        postService = PostService(postRepository)
    }

    @Test
    fun createPostTest() {
        val expected: PostResponse = postResponse()

        given(postRepository.save(any()))
            .willReturn(post())

        assertThat(postService.createPost(createPostRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun retrievePostTest() {
        val expected: PostResponse = postResponse()

        given(postRepository.findByPostId(any()))
            .willReturn(post())

        assertThat(postService.retrievePost(retrievePostRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noPost_retrievePostFailTest() {
        given(postRepository.findByPostId(any()))
            .willReturn(null)

        assertThrows(
            PostNotFoundException::class.java,
        ) {
            postService.retrievePost(retrievePostRequest())
        }
    }

    @Test
    fun updatePostTest() {
        val expected: PostResponse = updatedPostResponse()

        given(postRepository.findByPostId(any()))
            .willReturn(updatedPost())

        assertThat(postService.updatePost(updatePostRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noPost_updatePostFailTest() {
        given(postRepository.findByPostId(any()))
            .willThrow(PostNotFoundException.of(POST_ID.toString()))

        assertThrows(
            PostNotFoundException::class.java
        ) {
            postService.updatePost(updatePostRequest())
        }
    }
}