package me.jun.core.blog.application

import me.jun.core.blog.application.dto.PagedCategoryResponse
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun createCategoryOrElseGet(categoryName: String?): Category {
        val category: Category = Category(
            categoryId = null,
            name = categoryName!!,
            mappedArticleCount = 0L
        )

        return categoryRepository.findByName(categoryName)
            ?: categoryRepository.save(category)
    }

    fun retrievePagedCategories(pageable: Pageable?): PagedCategoryResponse {
        val pagedCategories: Page<Category> = categoryRepository.findAllBy(pageable)
        return PagedCategoryResponse.of(pagedCategories)
    }
}
