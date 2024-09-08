package me.jun.support

import me.jun.core.blog.application.dto.CategoryArticleResponse
import me.jun.core.blog.application.dto.CategoryResponse
import me.jun.core.blog.application.dto.CreateCategoryRequest
import me.jun.core.blog.application.dto.UpdateCategoryOfArticleRequest
import me.jun.core.blog.domain.Category

const val CATEGORY_ID: Long = 1L

const val CATEGORY_NAME: String = "category"

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

val updateCategoryOfArticleRequest: () -> UpdateCategoryOfArticleRequest = fun (): UpdateCategoryOfArticleRequest {
    return UpdateCategoryOfArticleRequest(
        articleId = ARTICLE_ID,
        newCategoryName = NEW_CATEGORY_NAME
    )
}

val categoryArticleResponse: () -> CategoryArticleResponse = fun (): CategoryArticleResponse {
    return CategoryArticleResponse(
        articleId = ARTICLE_ID,
        newCategoryName = NEW_CATEGORY_NAME
    )
}