package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo

data class CreateArticleRequest(
    val title: String,
    val content: String,
    val writerId: Long
) {

    fun toEntity(): Article {
        return Article(
            articleId = null,
            articleInfo = ArticleInfo(title, content),
            writerId = writerId,
            createdAt = null,
            updatedAt = null
        )
    }
}
