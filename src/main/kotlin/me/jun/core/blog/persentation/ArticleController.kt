package me.jun.core.blog.persentation

import jakarta.validation.Valid
import me.jun.common.security.WriterId
import me.jun.core.blog.application.ArticleService
import me.jun.core.blog.application.dto.ArticleResponse
import me.jun.core.blog.application.dto.CreateArticleRequest
import me.jun.core.blog.application.dto.RetrieveArticleRequest
import me.jun.core.blog.application.dto.UpdateArticleRequest
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
}