package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Tag
import me.jun.support.TAG_ID
import me.jun.support.TAG_NAME
import me.jun.support.tag
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Suppress("Deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagRepositoryTest {

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Test
    fun findByNameTest() {
        val expected: Tag = tag()

        tagRepository.save(
            tag().apply {
                this.tagId = null
            }
        )

        assertThat(tagRepository.findByName(TAG_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun findByNameFailTest() {
        assertThat(tagRepository.findByName(TAG_NAME))
            .isNull()
    }

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

    @Test
    fun findByTagIdFailTest() {
        assertThat(tagRepository.findByTagId(TAG_ID))
            .isNull()
    }
}