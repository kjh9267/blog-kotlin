package me.jun.core.blog.application

import jakarta.transaction.Transactional
import me.jun.core.blog.application.dto.ArticleResponse
import me.jun.core.blog.application.dto.CreateArticleRequest
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


}
