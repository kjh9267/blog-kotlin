package me.jun.support

import me.jun.core.guestbook.application.dto.*
import me.jun.core.guestbook.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.Instant
import java.time.Instant.now

const val POST_ID: Long = 1L

const val POST_TITLE: String = "post title string"

const val NEW_POST_TITLE: String = "new post title string"

const val POST_CONTENT: String = "post content string"

const val NEW_POST_CONTENT: String = "new post content string"

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

val updatedPost: () -> Post = fun (): Post {
    return Post(
        postId = POST_ID,
        title = NEW_POST_TITLE,
        content = NEW_POST_CONTENT,
        writerId = POST_WRITER_ID,
        createdAt = POST_CREATED_AT,
        updatedAt = POST_UPDATED_AT
    )
}

val postResponse: () -> PostResponse = fun (): PostResponse {
    return PostResponse(
        postId = POST_ID,
        title = POST_TITLE,
        content = POST_CONTENT,
        writerId = POST_WRITER_ID,
        createdAt = POST_CREATED_AT,
        updatedAt = POST_UPDATED_AT
    )
}

val createPostRequest: () -> CreatePostRequest = fun (): CreatePostRequest {
    return CreatePostRequest(
        title = POST_TITLE,
        content = POST_CONTENT,
        writerId = POST_WRITER_ID
    )
}

val retrievePostRequest: () -> RetrievePostRequest = fun (): RetrievePostRequest {
    return RetrievePostRequest(POST_ID)
}

val updatedPostResponse: () -> PostResponse = fun (): PostResponse {
    return PostResponse(
        postId = POST_ID,
        title = NEW_POST_TITLE,
        content = NEW_POST_CONTENT,
        writerId = POST_WRITER_ID,
        createdAt = POST_CREATED_AT,
        updatedAt = POST_UPDATED_AT
    )
}

val updatePostRequest: () -> UpdatePostRequest = fun (): UpdatePostRequest {
    return UpdatePostRequest(
        postId = POST_ID,
        newTitle = NEW_POST_TITLE,
        newContent = NEW_POST_CONTENT,
        writerId = POST_WRITER_ID
    )
}

val deletePostRequest: () -> DeletePostRequest = fun (): DeletePostRequest {
    return DeletePostRequest(
        postId = POST_ID
    )
}

val postList: () -> List<Post> = fun (): List<Post> {
    return (1..10).map {
        post().apply {
            this.postId = it.toLong()
        }
    }
}

val pagedPosts: () -> Page<Post> = fun (): Page<Post> {
    return PageImpl(postList())
}