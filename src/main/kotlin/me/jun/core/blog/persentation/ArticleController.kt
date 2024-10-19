package me.jun.core.blog.persentation

import jakarta.validation.Valid
import me.jun.common.security.WriterId
import me.jun.core.blog.application.ArticleService
import me.jun.core.blog.application.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/blog/articles")
class ArticleController(
    private val articleService: ArticleService
) {

    @PostMapping(
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun createArticle(
        @RequestBody @Valid request: CreateArticleRequest,
        @WriterId writerId: Long
    ): ResponseEntity<ArticleResponse> {
        val response = articleService.createArticle(
            request.apply { this.writerId = writerId }
        )

        return ResponseEntity.ok()
            .body(response)
    }

    @GetMapping(
        value = ["/{articleId}"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrieveArticle(
        @PathVariable articleId: Long
    ): ResponseEntity<ArticleResponse> {
        val response = articleService.retrieveArticle(RetrieveArticleRequest(articleId))

        return ResponseEntity.ok()
            .body(response)
    }

    @PutMapping(
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun updateArticle(
        @RequestBody @Valid request: UpdateArticleRequest,
        @WriterId writerId: Long
    ): ResponseEntity<ArticleResponse> {
        val articleResponse = articleService.updateArticle(
            request.apply { this.writerId = writerId }
        )
        return ResponseEntity.ok()
            .body(articleResponse)
    }

    @DeleteMapping("/{articleId}")
    fun deleteArticle(
        @PathVariable articleId: Long,
        @WriterId writerId: Long
    ): ResponseEntity<Void> {
        articleService.deleteArticle(
            DeleteArticleRequest(
                articleId = articleId,
                writerId = writerId
            )
        )
        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping(
        value = ["/query"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrievePagedArticles(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PagedArticleResponse> {
        val pagedArticleResponse = articleService.retrievePagedArticles(PageRequest.of(page, size))
        return ResponseEntity.ok()
            .body(pagedArticleResponse)
    }
}