package me.jun.core.blog.application

import me.jun.core.blog.application.dto.CategoryResponse
import me.jun.core.blog.application.dto.CreateCategoryRequest
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.CategoryRepository
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
}
