package me.jun.core.blog.application

import me.jun.core.blog.application.exception.TagNotFoundException
import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.repository.TagRepository
import me.jun.support.TAG_ID
import me.jun.support.TAG_NAME
import me.jun.support.tag
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Suppress("Deprecation")
class TagServiceTest {

    private lateinit var tagService: TagService

    @Mock
    private lateinit var tagRepository: TagRepository

    @BeforeEach
    fun setUp() {
        tagService = TagService(tagRepository)
    }

    @Test
    fun createTagTest() {
        val expected: Tag = tag()

        given(tagRepository.findByName(any()))
            .willReturn(null)

        given(tagRepository.save(any()))
            .willReturn(tag())

        assertThat(tagService.createTagOrElseGet(TAG_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun getTagTest() {
        val expected: Tag = tag()

        given(tagRepository.findByName(any()))
            .willReturn(tag())

        assertThat(tagService.createTagOrElseGet(TAG_NAME))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun retrieveTagTest() {
        val expected: Tag = tag()

        given(tagRepository.findByTagId(any()))
            .willReturn(tag())

        assertThat(tagService.retrieveTag(TAG_ID))
            .isEqualToComparingFieldByField(expected)
    }

    @Test
    fun retrieveTagFailTest() {
        given(tagRepository.findByTagId(any()))
            .willReturn(null)

        assertThrows(
            TagNotFoundException::class.java
        ) {
            tagService.retrieveTag(TAG_ID)
        }
    }
}