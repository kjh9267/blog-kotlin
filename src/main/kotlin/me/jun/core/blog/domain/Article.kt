package me.jun.core.blog.domain

import java.time.Instant

data class Article(
    var id: Long,
    var articleInfo: ArticleInfo,
    var writerId: Long,
    var createdAt: Instant,
    var updatedAt: Instant
) {
    fun updateArticleInfo(newTitle: String, newContent: String): Article {
        articleInfo = articleInfo.updateTitle(newTitle)
            .updateContent(newContent)

        return this;
    }
}
