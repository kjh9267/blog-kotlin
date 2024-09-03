package me.jun.core.blog.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class UpdateArticleRequest(
    @field:NotNull
    @field:Positive
    var articleId: Long,
    @field:NotBlank
    var newTitle: String,
    @field:NotBlank
    var newContent: String,
    var writerId: Long?
) {

}
