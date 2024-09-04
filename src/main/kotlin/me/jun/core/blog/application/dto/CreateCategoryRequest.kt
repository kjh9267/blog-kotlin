package me.jun.core.blog.application.dto

import jakarta.validation.constraints.NotBlank
import me.jun.core.blog.domain.Category

data class CreateCategoryRequest(
    @field:NotBlank
    var name: String
) {

    fun toEntity(): Category {
        return Category(
            categoryId = null,
            name = name,
            mappedArticleCount = 0L
        )
    }
}
