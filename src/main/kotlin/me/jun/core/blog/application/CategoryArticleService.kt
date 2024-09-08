package me.jun.core.blog.application

import me.jun.core.blog.application.dto.CategoryArticleResponse
import me.jun.core.blog.application.dto.PagedArticleResponse
import me.jun.core.blog.application.dto.UpdateCategoryOfArticleRequest
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.application.exception.CategoryNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.repository.CategoryRepository
import me.jun.core.blog.domain.service.CategoryMatchingService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryArticleService(
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository,
    private val categoryMatchingService: CategoryMatchingService
) {

    fun updateCategoryOfArticle(request: UpdateCategoryOfArticleRequest): CategoryArticleResponse {
        val article: Article = articleRepository.findByArticleId(request.articleId)
            ?: throw ArticleNotFoundException.of(request.articleId.toString())

        val oldCategory: Category = categoryRepository.findByCategoryId(article.categoryId)
            ?: throw CategoryNotFoundException.of(article.categoryId.toString())

        val newCategory: Category = categoryRepository.findByName(request.newCategoryName)
            ?: categoryRepository.save(
                Category(
                    categoryId = null,
                    name = request.newCategoryName,
                    mappedArticleCount = 0L
                )
            )

        categoryMatchingService.changeCategory(
            article = article,
            newCategory = newCategory,
            oldCategory = oldCategory
        )

        return CategoryArticleResponse(
            articleId = article.articleId!!,
            newCategoryName = request.newCategoryName
        )
    }

    fun retrievePagedCategoryArticles(categoryName: String?, pageable: Pageable?): PagedArticleResponse {
        val category: Category = categoryRepository.findByName(categoryName)
            ?: throw CategoryNotFoundException.of(categoryName!!)

        val pagedArticles = articleRepository.findAllByCategoryId(
            categoryId = category.categoryId,
            pageable = pageable
        )

        return PagedArticleResponse.of(pagedArticles)
    }
}
