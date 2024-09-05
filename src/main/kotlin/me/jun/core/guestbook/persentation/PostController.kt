package me.jun.core.guestbook.persentation

import jakarta.validation.Valid
import me.jun.common.security.WriterId
import me.jun.core.guestbook.application.PostService
import me.jun.core.guestbook.application.dto.CreatePostRequest
import me.jun.core.guestbook.application.dto.PostResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/guestbook/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping(
        produces = [APPLICATION_JSON_VALUE],
        consumes = [APPLICATION_JSON_VALUE]
    )
    fun createPost(
        @RequestBody @Valid request: CreatePostRequest,
        @WriterId writerId: Long
    ): ResponseEntity<PostResponse> {
        val postResponse: PostResponse = postService.createPost(request)

        return ResponseEntity.ok()
            .body(postResponse)
    }
}