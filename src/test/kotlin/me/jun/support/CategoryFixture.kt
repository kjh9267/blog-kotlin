package me.jun.support

import me.jun.core.blog.application.dto.CategoryArticleResponse
import me.jun.core.blog.application.dto.PagedCategoryResponse
import me.jun.core.blog.application.dto.UpdateCategoryOfArticleRequest
import me.jun.core.blog.domain.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

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

val categoryList: () -> List<Category> = fun (): List<Category> {
    return (1..10).map {
        category().apply {
            this.categoryId = it.toLong()
        }
    }
}

val pagedCategories: () -> Page<Category> = fun (): Page<Category> {
    return PageImpl(categoryList())
}

val pagedCategoryResponse: () -> PagedCategoryResponse = fun (): PagedCategoryResponse {
    return PagedCategoryResponse.of(pagedCategories())
}