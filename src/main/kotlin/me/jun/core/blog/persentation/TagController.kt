package me.jun.core.blog.persentation

import jakarta.validation.Valid
import me.jun.core.blog.application.TaggedArticleService
import me.jun.core.blog.application.dto.AddTagRequest
import me.jun.core.blog.application.dto.RetrieveTagListRequest
import me.jun.core.blog.application.dto.TagListResponse
import me.jun.core.blog.application.dto.TaggedArticleResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/blog/tags")
class TagController(
    private val taggedArticleService: TaggedArticleService
) {

    @PostMapping(
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun addTagToArticle(
        @RequestBody @Valid request: AddTagRequest
    ): ResponseEntity<TaggedArticleResponse> {
        val response: TaggedArticleResponse = taggedArticleService.addTagToArticle(request)
        return ResponseEntity.ok()
            .body(response)
    }

    @GetMapping(
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrieveTagList(
        @RequestParam("articleId") articleId: Long
    ): ResponseEntity<TagListResponse> {
        val request: RetrieveTagListRequest = RetrieveTagListRequest(
            articleId = articleId
        )
        val tagListResponse: TagListResponse = taggedArticleService.retrieveTagList(request)
        return ResponseEntity.ok()
            .body(tagListResponse)
    }
}