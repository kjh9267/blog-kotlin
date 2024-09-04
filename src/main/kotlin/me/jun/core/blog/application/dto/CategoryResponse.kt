package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Category

data class CategoryResponse(
    var categoryId: Long,
    var name: String,
    var mappedArticleCount: Long
) {

    companion object {
        fun of(category: Category): CategoryResponse {
            return CategoryResponse(
                categoryId = category.categoryId!!,
                name = category.name,
                mappedArticleCount = category.mappedArticleCount
            )
        }
    }
}
