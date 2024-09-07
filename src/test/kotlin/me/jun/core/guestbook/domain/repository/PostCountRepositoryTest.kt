package me.jun.core.guestbook.domain.repository

import me.jun.core.guestbook.domain.PostCount
import me.jun.support.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostCountRepositoryTest {

    @Autowired
    private lateinit var postCountRepository: PostCountRepository

    @Test
    fun findByPostIdTest() {
        val expected: PostCount = postCount()

        postCountRepository.save(
            PostCount(
                postCountId = POST_COUNT_ID,
                value = POST_COUNT_VALUE,
                postId = POST_ID,
                version = VERSION
            )
        )

        assertThat(postCountRepository.findByPostId(POST_COUNT_ID))
            .isEqualTo(expected)
    }

    @Test
    fun noPostCount_findByPostIdFailTest() {
        assertThat(postCountRepository.findByPostId(POST_COUNT_ID))
            .isNull()
    }
}
