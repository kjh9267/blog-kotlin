package me.jun.support

import me.jun.core.blog.application.dto.*
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo
import me.jun.core.blog.domain.Writer
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.Instant
import java.time.Instant.now

const val ARTICLE_ID: Long = 1L;

const val ARTICLE_TITLE: String = "title string"

const val ARTICLE_NEW_TITLE: String = "new title string";

const val ARTICLE_CONTENT: String = "content string"

const val ARTICLE_NEW_CONTENT: String = "new content string"

const val ARTICLE_WRITER_ID: Long = 1L;

val ARTICLE_CREATED_AT: Instant = now();

val ARTICLE_UPDATED_AT: Instant = now();

val article: () -> Article = fun (): Article {
    return Article(
        articleId = ARTICLE_ID,
        categoryId = CATEGORY_ID,
        articleInfo = articleInfo(),
        writer = writer(),
        createdAt = ARTICLE_CREATED_AT,
        updatedAt = ARTICLE_UPDATED_AT
    )
}

val writer: () -> Writer = fun (): Writer {
    return Writer(POST_WRITER_ID)
}

val articleInfo: () -> ArticleInfo = fun (): ArticleInfo {
    return ArticleInfo(
        title = ARTICLE_TITLE,
        content = ARTICLE_CONTENT
    )
}

val updatedArticle: () -> Article = fun (): Article {
    return Article(
        articleId = ARTICLE_ID,
        categoryId = CATEGORY_ID,
        articleInfo = updatedArticleInfo(),
        writer = writer(),
        createdAt = ARTICLE_CREATED_AT,
        updatedAt = ARTICLE_UPDATED_AT
    )
}

val updatedArticleInfo: () -> ArticleInfo = fun (): ArticleInfo {
    return ArticleInfo(
        title = ARTICLE_NEW_TITLE,
        content = ARTICLE_NEW_CONTENT
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
        title = ARTICLE_TITLE,
        content = ARTICLE_CONTENT,
        categoryName = CATEGORY_NAME,
        writerId = ARTICLE_WRITER_ID
    )
}

val retrieveArticleRequest: () -> RetrieveArticleRequest = fun (): RetrieveArticleRequest {
    return RetrieveArticleRequest(ARTICLE_ID)
}

val updateArticleRequest: () -> UpdateArticleRequest = fun (): UpdateArticleRequest {
    return UpdateArticleRequest(
        articleId = ARTICLE_ID,
        newTitle = ARTICLE_NEW_TITLE,
        newContent = ARTICLE_NEW_CONTENT,
        writerId = ARTICLE_WRITER_ID
    )
}

val deleteArticleRequest: () -> DeleteArticleRequest = fun (): DeleteArticleRequest {
    return DeleteArticleRequest(ARTICLE_ID)
}

val articleList: () -> List<Article> = fun (): List<Article> {
    return (1..10).map {
        article().apply {
            this.articleId = it.toLong()
        }
    }
}

val pagedArticles: () -> Page<Article> = fun (): Page<Article> {
    return PageImpl(articleList())
}

val pagedArticleResponse: () -> PagedArticleResponse = fun (): PagedArticleResponse {
    return PagedArticleResponse.of(pagedArticles())
}