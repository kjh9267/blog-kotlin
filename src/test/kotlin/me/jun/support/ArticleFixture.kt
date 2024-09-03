package me.jun.support

import me.jun.core.blog.application.dto.*
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo
import java.time.Instant
import java.time.Instant.now

const val ARTICLE_ID: Long = 1L;

const val TITLE: String = "title string"

const val NEW_TITLE: String = "new title string";

const val CONTENT: String = "content string"

const val NEW_CONTENT: String = "new content string"

const val WRITER_ID: Long = 1L;

val ARTICLE_CREATED_AT: Instant = now();

val ARTICLE_UPDATED_AT: Instant = now();

val article: () -> Article = fun (): Article {
    return Article(
        articleId = ARTICLE_ID,
        articleInfo = articleInfo(),
        writerId = WRITER_ID,
        createdAt = ARTICLE_CREATED_AT,
        updatedAt = ARTICLE_UPDATED_AT
    )
}

val articleInfo: () -> ArticleInfo = fun (): ArticleInfo {
    return ArticleInfo(
        title = TITLE,
        content = CONTENT
    )
}

val updatedArticle: () -> Article = fun (): Article {
    return Article(
        articleId = ARTICLE_ID,
        articleInfo = updatedArticleInfo(),
        writerId = WRITER_ID,
        createdAt = ARTICLE_CREATED_AT,
        updatedAt = ARTICLE_UPDATED_AT
    )
}

val updatedArticleInfo: () -> ArticleInfo = fun (): ArticleInfo {
    return ArticleInfo(
        title = NEW_TITLE,
        content = NEW_CONTENT
    )
}

val articleResponse: () -> ArticleResponse = fun (): ArticleResponse {
    return ArticleResponse.of(article())
}

val updatedArticleResponse: () -> ArticleResponse = fun (): ArticleResponse {
    return ArticleResponse.of(updatedArticle())
}

val createArticleRequest: () -> CreateArticleRequest = fun (): CreateArticleRequest {
    return CreateArticleRequest(
        title = TITLE,
        content = CONTENT,
        writerId = WRITER_ID
    )
}

val retrieveArticleRequest: () -> RetrieveArticleRequest = fun (): RetrieveArticleRequest {
    return RetrieveArticleRequest(ARTICLE_ID)
}

val updateArticleRequest: () -> UpdateArticleRequest = fun (): UpdateArticleRequest {
    return UpdateArticleRequest(
        articleId = ARTICLE_ID,
        newTitle = NEW_TITLE,
        newContent = NEW_CONTENT,
        writerId = WRITER_ID
    )
}

val deleteArticleRequest: () -> DeleteArticleRequest = fun (): DeleteArticleRequest {
    return DeleteArticleRequest(ARTICLE_ID)
}