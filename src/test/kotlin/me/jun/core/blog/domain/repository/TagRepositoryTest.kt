package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Tag
import me.jun.support.TAG_ID
import me.jun.support.tag
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
class TagRepositoryTest {

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Test
    fun findByTagIdTest() {
        val expected: Tag = tag()

        tagRepository.save(
            tag().apply {
                this.tagId = null
            }
        )

        assertThat(tagRepository.findByTagId(TAG_ID))
            .isEqualToComparingFieldByField(expected)
    }
}