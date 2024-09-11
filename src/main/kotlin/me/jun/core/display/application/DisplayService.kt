package me.jun.core.display.application

import me.jun.core.display.application.dto.ArticleResponse
import me.jun.core.display.application.dto.PagedArticleResponse
import me.jun.core.display.domain.repository.DisplayRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DisplayService(
    private val displayRepositoryImpl: DisplayRepository<ArticleResponse>
) {

    fun retrieveDisplay(page: Int, size: Int): PagedArticleResponse {
        val result: Page<ArticleResponse> = displayRepositoryImpl.retrieveDisplay(page, size)

        return PagedArticleResponse.of(result)
    }
}