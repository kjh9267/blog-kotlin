package me.jun.support

import me.jun.core.display.application.dto.ArticleResponse
import me.jun.core.display.application.dto.PagedArticleResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

val displayArticleResponse: () -> ArticleResponse = fun(): ArticleResponse {
    return ArticleResponse(
        articleId = ARTICLE_ID,
        title = ARTICLE_TITLE,
        content = ARTICLE_CONTENT,
        categoryName = CATEGORY_NAME,
        writerName = MEMBER_NAME
    )
}

val displayPageArticleResponse: () -> Page<ArticleResponse> = fun (): Page<ArticleResponse> {
    return PageImpl(
        (1..10).map {
            displayArticleResponse().apply {
                this.articleId = it.toLong()
            }
        }
    )
}

val displayPagedArticleResponse: () -> PagedArticleResponse = fun(): PagedArticleResponse {
    return PagedArticleResponse.of(displayPageArticleResponse())
}