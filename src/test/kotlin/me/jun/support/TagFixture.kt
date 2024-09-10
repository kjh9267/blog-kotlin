package me.jun.support

import me.jun.core.blog.application.dto.AddTagRequest
import me.jun.core.blog.application.dto.RetrieveTagListRequest
import me.jun.core.blog.application.dto.TagListResponse
import me.jun.core.blog.application.dto.TaggedArticleResponse
import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.TaggedArticle

const val TAG_ID: Long = 1L

const val TAG_NAME: String = "tag name"

const val TAGGED_ARTICLE_ID: Long = 1L

val tag: () -> Tag = fun(): Tag {
    return Tag(
        tagId = TAG_ID,
        name = TAG_NAME
    )
}

val taggedArticle: () -> TaggedArticle = fun(): TaggedArticle {
    return TaggedArticle(
        taggedArticleId = TAGGED_ARTICLE_ID,
        tagId = TAG_ID,
        articleId = ARTICLE_ID
    )
}

val addTagRequest: () -> AddTagRequest = fun(): AddTagRequest {
    return AddTagRequest(
        articleId = ARTICLE_ID,
        tagName = TAG_NAME
    )
}

val taggedArticleResponse: () -> TaggedArticleResponse = fun (): TaggedArticleResponse {
    return TaggedArticleResponse(
        taggedArticleId = TAGGED_ARTICLE_ID,
        tagId = TAG_ID,
        articleId = ARTICLE_ID
    )
}

val retrieveTagListRequest: () -> RetrieveTagListRequest = fun(): RetrieveTagListRequest {
    return RetrieveTagListRequest(
        articleId = ARTICLE_ID
    )
}

val taggedArticles: () -> List<TaggedArticle> = fun (): List<TaggedArticle> {
    return List(10) { taggedArticle() }
}

val tags: () -> List<Tag> = fun (): List<Tag> {
    return List(10) { tag() }
}

val tagListResponse: () -> TagListResponse = fun (): TagListResponse {
    return TagListResponse.of(tags())
}