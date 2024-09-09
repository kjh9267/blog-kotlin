package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import java.time.Instant

data class ArticleResponse(
    var id: Long?,
    var title: String,
    var content: String,
    var writerId: Long,
    var categoryId: Long,
    var createdAt: Instant?,
    var updatedAt: Instant?
) {

    companion object {
        fun of(article: Article): ArticleResponse {
            return ArticleResponse(
                id = article.articleId,
                title = article.articleInfo!!.title,
                content = article.articleInfo!!.content,
                writerId = article.writer.value,
                categoryId = article.categoryId!!,
                createdAt = article.createdAt,
                updatedAt = article.updatedAt
            )
        }
    }
}
