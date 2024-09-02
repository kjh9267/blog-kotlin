package me.jun.core.blog.application

import jakarta.transaction.Transactional
import me.jun.core.blog.application.dto.*
import me.jun.core.blog.application.exception.ArticleNotFoundException
import me.jun.core.blog.domain.Article
import me.jun.core.blog.domain.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
@Transactional
class ArticleService(private val articleRepository: ArticleRepository) {

    fun createArticle(request: CreateArticleRequest): ArticleResponse {
        val article = request.toEntity()
        val savedArticle = articleRepository.save(article)

        return ArticleResponse.of(savedArticle)
    }

    fun retrieveArticle(request: RetrieveArticleRequest): ArticleResponse {
        val article: Article = articleRepository.findByArticleId(request.id)
            ?: throw ArticleNotFoundException.of(request.id.toString())

        return ArticleResponse.of(article)
    }

    fun updateArticle(request: UpdateArticleRequest): ArticleResponse {
        val article: Article = articleRepository.findByArticleId(request.articleId)
            ?: throw ArticleNotFoundException.of(request.articleId.toString())

        val updatedArticle: Article = article.updateArticleInfo(request.newTitle, request.newContent)

        return ArticleResponse.of(updatedArticle)
    }

    fun deleteArticle(request: DeleteArticleRequest): Unit {
        articleRepository.deleteById(request.articleId)
    }
}
