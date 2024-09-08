package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Category
import org.springframework.data.domain.Page

data class PagedCategoryResponse(
    val categoryResponses: Page<CategoryResponse>
) {

    companion object {
        fun of(categories: Page<Category>): PagedCategoryResponse {
            return PagedCategoryResponse(
                categories.map { CategoryResponse.of(it) }
            )
        }
    }
}
