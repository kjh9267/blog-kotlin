package me.jun.support

import me.jun.core.blog.application.dto.CategoryResponse
import me.jun.core.blog.application.dto.CreateCategoryRequest
import me.jun.core.blog.domain.Category

const val CATEGORY_ID: Long = 1L

const val CATEGORY_NAME: String = "category name string"

const val NEW_CATEGORY_NAME: String = "new category name string"

const val INITIAL_MAPPED_ARTICLE_COUNT: Long = 0L

const val MAPPED_ARTICLE_COUNT: Long = 1L

val category: () -> Category = fun (): Category {
    return Category(
        categoryId = CATEGORY_ID,
        name = CATEGORY_NAME,
        mappedArticleCount = INITIAL_MAPPED_ARTICLE_COUNT
    )
}

val createCategoryRequest: () -> CreateCategoryRequest = fun (): CreateCategoryRequest {
    return CreateCategoryRequest(
        name = CATEGORY_NAME
    )
}

val categoryResponse: () -> CategoryResponse = fun (): CategoryResponse {
    return CategoryResponse(
        categoryId = CATEGORY_ID,
        name = CATEGORY_NAME,
        mappedArticleCount = INITIAL_MAPPED_ARTICLE_COUNT
    )
}