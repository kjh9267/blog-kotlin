package me.jun.core.guestbook.domain.repository

import me.jun.core.guestbook.domain.Post
import me.jun.support.*
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
class PostRepositoryTest {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Test
    fun findByPostIdTest() {
        val expected: Post = Post(
            postId = POST_ID,
            title = POST_TITLE,
            content = POST_CONTENT,
            writerId = POST_WRITER_ID,
            createdAt = POST_CREATED_AT,
            updatedAt = POST_UPDATED_AT
        )

        postRepository.save(post())

        assertThat(postRepository.findByPostId(POST_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun noPost_findByPostIdFailTest() {
        assertThat(postRepository.findByPostId(POST_ID))
            .isNull()
    }
}