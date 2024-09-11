package me.jun.core.blog.application


import me.jun.core.blog.application.dto.*
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.Category
import me.jun.core.blog.domain.repository.ArticleRepository
import me.jun.core.blog.domain.service.CategoryMatchingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val categoryService: CategoryService,
    private val categoryMatchingService: CategoryMatchingService
) {

    fun createArticle(request: CreateArticleRequest?): ArticleResponse {
        val article: Article = request!!.toEntity()
        val category: Category = categoryService.createCategoryOrElseGet(request.categoryName)
        categoryMatchingService.matchCategory(article, category)
        val savedArticle: Article = articleRepository.save(article)

        return ArticleResponse.of(savedArticle)
    }

    @Transactional(readOnly = true)
    fun retrieveArticle(request: RetrieveArticleRequest?): ArticleResponse {
        val article: Article = articleRepository.findByArticleId(request?.id)
            ?: throw ArticleNotFoundException.of(request?.id.toString())

        return ArticleResponse.of(article)
    }

    fun updateArticle(request: UpdateArticleRequest?): ArticleResponse {
        val article: Article = articleRepository.findByArticleId(request!!.articleId)
            ?: throw ArticleNotFoundException.of(request.articleId.toString())

        article.writer.validate(request.writerId)

        val updatedArticle: Article = article.updateArticleInfo(
            writerId = request.writerId!!,
            newTitle = request.newTitle,
            newContent = request.newContent
        )

        return ArticleResponse.of(updatedArticle)
    }

    fun deleteArticle(request: DeleteArticleRequest?): Unit {
        articleRepository.deleteById(request!!.articleId)
    }

    fun retrievePagedArticles(request: Pageable?): PagedArticleResponse {
        val pagedArticles: Page<Article> = articleRepository.findAllBy(request)
        return PagedArticleResponse.of(pagedArticles)
    }
}
