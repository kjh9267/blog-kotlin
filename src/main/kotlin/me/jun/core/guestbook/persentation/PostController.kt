package me.jun.core.guestbook.persentation

import jakarta.validation.Valid
import me.jun.common.security.WriterId
import me.jun.core.guestbook.application.PostService
import me.jun.core.guestbook.application.dto.CreatePostRequest
import me.jun.core.guestbook.application.dto.PostResponse
import me.jun.core.guestbook.application.dto.RetrievePostRequest
import me.jun.core.guestbook.application.dto.UpdatePostRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping(
        value = ["/{postId}"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrievePost(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        val request: RetrievePostRequest = RetrievePostRequest(postId)
        val postResponse: PostResponse = postService.retrievePost(request)

        return ResponseEntity.ok()
            .body(postResponse)
    }

    @PutMapping(
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun updatePost(
        @RequestBody @Valid request: UpdatePostRequest,
        @WriterId writerId: Long
        ): ResponseEntity<PostResponse> {
        val postResponse: PostResponse = postService.updatePost(request)

        return ResponseEntity.ok()
            .body(postResponse)
    }
}