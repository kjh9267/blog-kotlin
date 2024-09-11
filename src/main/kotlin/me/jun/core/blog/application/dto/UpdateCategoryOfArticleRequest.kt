package me.jun.core.blog.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class UpdateCategoryOfArticleRequest(
    @field:NotNull
    @field:Positive
    var articleId: Long,
    @field:NotBlank
    var newCategoryName: String,
    var writerId: Long
) {

}
