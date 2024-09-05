package me.jun.core.guestbook.application.dto

import me.jun.core.guestbook.domain.Post
import java.time.Instant

data class PostResponse(
    var postId: Long,
    var title: String,
    var content: String,
    var writerId: Long,
    var createdAt: Instant,
    var updatedAt: Instant
) {

    companion object {
        fun of(post: Post): PostResponse {
            return PostResponse(
                postId = post.postId!!,
                title = post.title,
                content = post.content,
                writerId = post.writerId,
                createdAt = post.createdAt!!,
                updatedAt = post.updatedAt!!
            )
        }
    }
}
