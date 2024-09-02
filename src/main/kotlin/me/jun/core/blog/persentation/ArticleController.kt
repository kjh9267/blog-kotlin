package me.jun.core.blog.persentation

import me.jun.core.blog.application.ArticleService
import me.jun.core.blog.application.dto.ArticleResponse
import me.jun.core.blog.application.dto.RetrieveArticleRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blog/articles")
class ArticleController(
    private val articleService: ArticleService
) {

    @GetMapping(
        value = ["/{articleId}"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrieveArticle(@PathVariable articleId: Long): ResponseEntity<ArticleResponse> {
        val response = articleService.retrieveArticle(RetrieveArticleRequest(articleId))

        return ResponseEntity.ok()
            .body(response)
    }
}