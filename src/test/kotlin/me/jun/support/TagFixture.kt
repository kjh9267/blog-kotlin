package me.jun.support

import me.jun.core.blog.domain.Tag
import me.jun.core.blog.domain.TaggedArticle

const val TAG_ID: Long = 1L

const val TAG_NAME: String = "tag name"

const val TAGGED_ARTICLE_ID: Long = 1L

const val TAGGED_ARTICLE_NAME: String = "tagged article name"

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