package me.jun.core.blog.application.dto

data class UpdateArticleRequest(
    val articleId: Long,
    val newTitle: String,
    val newContent: String,
    val writerId: Long
) {

}
