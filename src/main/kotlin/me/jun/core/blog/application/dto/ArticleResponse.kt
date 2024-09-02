package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import java.time.Instant

data class ArticleResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val writerId: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?
) {

    companion object {
        fun of(article: Article): ArticleResponse {
            return ArticleResponse(
                id = article.articleId,
                title = article.articleInfo!!.title,
                content = article.articleInfo!!.content,
                writerId = article.writerId,
                createdAt = article.createdAt,
                updatedAt = article.updatedAt
            )
        }
    }
}
