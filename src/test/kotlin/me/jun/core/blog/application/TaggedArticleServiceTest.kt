package me.jun.core.blog.application

import me.jun.core.blog.application.dto.TagListResponse
import me.jun.core.blog.domain.repository.TaggedArticleRepository
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
class TaggedArticleServiceTest {

    private lateinit var taggedArticleService: TaggedArticleService

    @Mock
    private lateinit var taggedArticleRepository: TaggedArticleRepository

    @Mock
    private lateinit var tagService: TagService

    @BeforeEach
    fun setUp() {
        taggedArticleService = TaggedArticleService(
            taggedArticleRepository = taggedArticleRepository,
            tagService = tagService
        )
    }

    @Test
    fun addTagToArticleTest() {
        given(tagService.createTagOrElseGet(any()))
            .willReturn(tag())

        given(taggedArticleRepository.save(any()))
            .willReturn(taggedArticle())

        assertThat(taggedArticleService.addTagToArticle(addTagRequest()))
            .isEqualToComparingFieldByField(taggedArticleResponse())
    }

    @Test
    fun retrieveTagListTest() {
        val expected: TagListResponse = tagListResponse()

        given(taggedArticleRepository.findAllByArticleId(any()))
            .willReturn(taggedArticles())

        given(tagService.retrieveTag(any()))
            .willReturn(tag())

        assertThat(taggedArticleService.retrieveTagList(retrieveTagListRequest()))
            .isEqualTo(expected)
    }
}