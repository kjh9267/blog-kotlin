package me.jun.support

import me.jun.core.guestbook.domain.Post
import java.time.Instant
import java.time.Instant.now

const val POST_ID: Long = 1L

const val POST_TITLE: String = "post title string"

const val POST_CONTENT: String = "post content string"

const val POST_WRITER_ID: Long = 1L

val POST_CREATED_AT: Instant = now()

val POST_UPDATED_AT: Instant = now()

val post: () -> Post = fun (): Post {
    return Post(
        postId = POST_ID,
        title = POST_TITLE,
        content = POST_CONTENT,
        writerId = POST_WRITER_ID,
        createdAt = POST_CREATED_AT,
        updatedAt = POST_UPDATED_AT
    )
}