package me.jun.core.blog.application.dto

import jakarta.validation.constraints.NotBlank
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.ArticleInfo

data class CreateArticleRequest(
    @field:NotBlank
    var title: String,
    @field:NotBlank
    var content: String,
    var writerId: Long?
) {

    fun toEntity(): Article {
        return Article(
            articleId = null,
            categoryId = null,
            articleInfo = ArticleInfo(title, content),
            writerId = writerId!!,
            createdAt = null,
            updatedAt = null
        )
    }
}
