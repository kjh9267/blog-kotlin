package me.jun.core.guestbook.application

import me.jun.core.guestbook.domain.PostCount
import me.jun.core.guestbook.domain.repository.PostCountRepository
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
class PostCountServiceTest {

    private lateinit var postCountService: PostCountService

    @Mock
    private lateinit var postCountRepository: PostCountRepository

    @BeforeEach
    fun setUp() {
        postCountService = PostCountService(postCountRepository)
    }

    @Test
    fun createPostCountTest() {
        val expected: PostCount = PostCount(
            postCountId = POST_COUNT_ID,
            value = POST_COUNT_VALUE,
            postId = POST_ID,
            version = VERSION
        )

        given(postCountRepository.save(any()))
            .willReturn(postCount())

        assertThat(postCountService.createPostCount(POST_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun incrementPostCountTest() {
        given(postCountRepository.findByPostId(any()))
            .willReturn(postCount())

        assertThat(postCountService.incrementPostCount(POST_ID))
            .isEqualTo(1L)
    }
}