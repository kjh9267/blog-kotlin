package me.jun.core.blog.application.dto

data class DeleteArticleRequest(
    var articleId: Long
) {

    companion object {
        fun of(articleId: Long): DeleteArticleRequest {
            return DeleteArticleRequest(articleId)
        }
    }
}