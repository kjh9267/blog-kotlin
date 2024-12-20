package me.jun.core.blog.application

import me.jun.core.blog.application.dto.AddTagRequest
import me.jun.core.blog.application.dto.RetrieveTagListRequest
import me.jun.core.blog.application.dto.TagListResponse
import me.jun.core.blog.application.dto.TaggedArticleResponse
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.TaggedArticle
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.repository.TaggedArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaggedArticleService(
    private val articleRepository: ArticleRepository,
    private val taggedArticleRepository: TaggedArticleRepository,
    private val tagService: TagService
) {

    fun addTagToArticle(request: AddTagRequest?): TaggedArticleResponse {
        val article: Article = articleRepository.findByArticleId(request!!.articleId)
            ?: throw ArticleNotFoundException.of(request.articleId.toString())

        article.writer.validate(request.writerId)

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

    @Transactional(readOnly = true)
    fun retrieveTagList(request: RetrieveTagListRequest?): TagListResponse {
        val taggedArticles: List<TaggedArticle> = taggedArticleRepository.findAllByArticleId(request!!.articleId)
        val tags: MutableList<Tag> = mutableListOf()

        for (taggedArticle: TaggedArticle in taggedArticles) {
            val tag: Tag = tagService.retrieveTag(taggedArticle.tagId!!)
            tags.add(tag)
        }

        return TagListResponse.of(tags)
    }
}
