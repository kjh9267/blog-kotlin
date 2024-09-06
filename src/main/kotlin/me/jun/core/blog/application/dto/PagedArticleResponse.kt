package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import org.springframework.data.domain.Page

data class PagedArticleResponse(
    var articleResponses: Page<ArticleResponse>
) {

    companion object {
        fun of(articles: Page<Article>): PagedArticleResponse {
            return PagedArticleResponse(
                articles.map { ArticleResponse.of(it) }
            )
        }
    }
}
