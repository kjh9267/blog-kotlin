package me.jun.core.display.application.dto

import org.springframework.data.domain.Page

class PagedArticleResponse(
    var page: Page<ArticleResponse>
) {

    companion object {
        fun of(page: Page<ArticleResponse>): PagedArticleResponse {
            return PagedArticleResponse(page)
        }
    }
}