package me.jun.core.guestbook.application

import me.jun.core.guestbook.application.dto.PagedPostResponse
import me.jun.core.guestbook.application.dto.PostResponse
import me.jun.core.guestbook.application.exception.PostNotFoundException
import me.jun.core.guestbook.domain.Writer
import me.jun.core.guestbook.domain.exception.WriterMismatchException
import me.jun.core.guestbook.domain.repository.PostRepository
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class PostServiceTest {

    private lateinit var postService: PostService

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var postCountService: PostCountService

    @BeforeEach
    fun setUp() {
        postService = PostService(
            postRepository = postRepository,
            postCountService = postCountService
        )
    }

    @Test
    fun createPostTest() {
        val expected: PostResponse = postResponse()

        given(postRepository.save(any()))
            .willReturn(post())

        given(postCountService.createPostCount(any()))
            .willReturn(postCount())

        assertThat(postService.createPost(createPostRequest()))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun retrievePostTest() {
        val expected: PostResponse = postResponse()

        given(postRepository.findByPostId(any()))
            .willReturn(post())

        given(postCountService.incrementPostCount(any()))
            .willReturn(POST_COUNT_VALUE + 1)

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
    fun invalidWriter_updatePostFailTest() {
        given(postRepository.findByPostId(any()))
            .willReturn(
                post().apply {
                    this.writer.value = 2L
                }
            )

        assertThrows(
            WriterMismatchException::class.java
        ) {
            postService.updatePost(updatePostRequest())
        }
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

    @Test
    fun deletePostTest() {
        given(postRepository.findByPostId(any()))
            .willReturn(post())

        doNothing()
            .`when`(postCountService)
            .deletePostCount(any())

        doNothing()
            .`when`(postRepository)
            .deleteById(any())

        postService.deletePost(deletePostRequest())

        verify(postCountService)
            .deletePostCount(POST_ID)

        verify(postRepository)
            .deleteById(POST_ID)
    }

    @Test
    fun noPost_deletePostFailTest() {
        given(postRepository.findByPostId(any()))
            .willReturn(null)

        assertThrows(PostNotFoundException::class.java) {
            postService.deletePost(deletePostRequest())
        }
    }

    @Test
    fun invalidWriter_deletePostFailTEst() {
        given(postRepository.findByPostId(any()))
            .willReturn(
                post().apply {
                    this.writer = Writer(2L)
                }
            )

        assertThrows(WriterMismatchException::class.java) {
            postService.deletePost(deletePostRequest())
        }
    }

    @Test
    fun retrievePagedPostsTest() {
        val expected: PagedPostResponse = pagedPostResponse()

        given(postRepository.findAllBy(any()))
            .willReturn(pagedPosts())

        assertThat(postService.retrievePagedPosts(PageRequest.of(0, 10)))
            .isEqualToComparingFieldByField(expected)
    }
}