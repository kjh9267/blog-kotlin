package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo

data class UpdateArticleRequest(
    val articleId: Long,
    val newTitle: String,
    val newContent: String,
    val writerId: Long
) {

}
