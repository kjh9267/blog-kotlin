package me.jun.core.blog.application

import me.jun.core.blog.application.dto.AddTagRequest
import me.jun.core.blog.application.dto.TaggedArticleResponse
import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.TaggedArticle
import me.jun.core.blog.domain.repository.TaggedArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaggedArticleService(
    private val taggedArticleRepository: TaggedArticleRepository,
    private val tagService: TagService
) {

    fun addTagToArticle(request: AddTagRequest): TaggedArticleResponse {
        val tag: Tag = tagService.createTagOrElseGet(request.tagName)
        var taggedArticle: TaggedArticle = TaggedArticle(
            taggedArticleId = null,
            tagId = null,
            articleId = null
        )

        taggedArticle = taggedArticle.match(
            tagId = tag.tagId,
            articleId = request.articleId
        )

        val savedTaggedArticle: TaggedArticle = taggedArticleRepository.save(taggedArticle)

        return TaggedArticleResponse.of(savedTaggedArticle)
    }
}
