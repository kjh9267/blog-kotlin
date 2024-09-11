package me.jun.core.display.infra

import jakarta.persistence.EntityManager
import me.jun.core.display.application.dto.ArticleResponse
import me.jun.core.display.domain.repository.DisplayRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@Component
class DisplayRepositoryImpl(
    private val entityManager: EntityManager
): DisplayRepository<ArticleResponse> {

    override fun retrieveDisplay(page: Int?, size: Int?): Page<ArticleResponse> {
        val query = "SELECT a.article_id, a.title, a.content, c.name, m.name member_name FROM article a JOIN category c ON a.category_id = c.category_id JOIN member m ON a.writer_id = m.member_id ORDER BY a.article_id ASC LIMIT ? OFFSET ?"

        val resultList: List<ArticleResponse> = entityManager.createNativeQuery(query, ArticleResponse::class.java)
            .setParameter(1, size!!)
            .setParameter(2, page!! * size)
            .resultList as List<ArticleResponse>

        return PageImpl(resultList)
    }
}