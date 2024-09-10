package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.TaggedArticle

data class TaggedArticleResponse(
    var taggedArticleId: Long,
    var tagId: Long,
    var articleId: Long
) {

    companion object {
        fun of(taggedArticle: TaggedArticle): TaggedArticleResponse {
            return TaggedArticleResponse(
                taggedArticleId = taggedArticle.taggedArticleId!!,
                tagId = taggedArticle.tagId!!,
                articleId = taggedArticle.articleId!!
            )
        }
    }
}
