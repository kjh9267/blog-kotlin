package me.jun.core.guestbook.persentation

import jakarta.validation.Valid
import me.jun.common.security.WriterId
import me.jun.core.guestbook.application.PostService
import me.jun.core.guestbook.application.dto.*
import org.springframework.data.domain.PageRequest
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
        val postResponse: PostResponse = postService.createPost(
            request.apply {
                this.writerId = writerId
            }
        )

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
        val postResponse: PostResponse = postService.updatePost(
            request.apply {
                this.writerId = writerId
            }
        )

        return ResponseEntity.ok()
            .body(postResponse)
    }

    @DeleteMapping(value = ["/{postId}"])
    fun deletePost(
        @PathVariable postId: Long,
        @WriterId writerId: Long
        ): ResponseEntity<Void> {
        val request: DeletePostRequest = DeletePostRequest(
            postId = postId,
            writerId = writerId
        )
        postService.deletePost(request)

        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping(
        value = ["/query"],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun retrievePagedPosts(
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<PagedPostResponse> {
        val pagedPostResponse = postService.retrievePagedPosts(PageRequest.of(page, size))
        return ResponseEntity.ok()
            .body(pagedPostResponse)
    }
}