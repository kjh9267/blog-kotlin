package me.jun.core.blog.application.dto

data class DeleteArticleRequest(
    var articleId: Long,
    var writerId: Long
) {
}