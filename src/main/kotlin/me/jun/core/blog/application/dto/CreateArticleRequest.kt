package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo

data class CreateArticleRequest(
    private val title: String,
    private val content: String,
    private val writerId: Long
) {

    fun toEntity(): Article {
        return Article(
            id = null,
            articleInfo = ArticleInfo(title, content),
            writerId = writerId,
            createdAt = null,
            updatedAt = null
        )
    }
}
