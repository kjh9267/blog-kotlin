package me.jun.core.guestbook.domain.repository

import me.jun.core.guestbook.domain.Post
import me.jun.support.POST_ID
import me.jun.support.post
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import java.time.Instant.now

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostRepositoryTest {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun findByPostIdTest() {
        val expected: Post = post()

        postRepository.save(
            post().apply {
                this.postId = null
                this.createdAt = null
                this.updatedAt = null
            }
        )

        val post: Post = postRepository.findByPostId(POST_ID)!!

        assertAll(
            {
                assertThat(post)
                    .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt")
            },
            {
                assertThat(post.createdAt)
                    .isBeforeOrEqualTo(now())
            },
            {
                assertThat(post.updatedAt)
                    .isBeforeOrEqualTo(now())
            }
        )
    }

    @Test
    fun noPost_findByPostIdFailTest() {
        assertThat(postRepository.findByPostId(POST_ID))
            .isNull()
    }

    @Test
    fun findAllByTest() {
        val expected: Long = 10L

        for (id in 1..20) {
            postRepository.save(
                post().apply {
                    this.postId = id.toLong()
                }
            )
        }

        val page: Page<Post> = postRepository.findAllBy(PageRequest.of(0, 10))

        assertThat(page.numberOfElements)
            .isEqualTo(expected)
    }
}