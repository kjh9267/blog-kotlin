package me.jun.support

import me.jun.core.guestbook.application.dto.*
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.PostCount
import me.jun.core.guestbook.domain.PostInfo
import me.jun.core.guestbook.domain.Writer
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

const val POST_COUNT_ID: Long = 1L

const val POST_COUNT_VALUE: Long = 0L

const val VERSION: Long = 0L

val POST_CREATED_AT: Instant = now()

val POST_UPDATED_AT: Instant = now()

val post: () -> Post = fun (): Post {
    return Post(
        postId = POST_ID,
        postInfo = postInfo(),
        writer = postWriter(),
        createdAt = POST_CREATED_AT,
        updatedAt = POST_UPDATED_AT
    )
}

val postWriter: () -> Writer = fun (): Writer {
    return Writer(
        value = POST_WRITER_ID
    )
}

val postInfo: () -> PostInfo = fun (): PostInfo {
    return PostInfo(
        title = POST_TITLE,
        content = POST_CONTENT
    )
}

val postCount: () -> PostCount = fun (): PostCount {
    return PostCount(
        postCountId = POST_COUNT_ID,
        value = POST_COUNT_VALUE,
        postId = POST_ID,
        version = VERSION
    )
}

val updatedPost: () -> Post = fun (): Post {
    return Post(
        postId = POST_ID,
        postInfo = postInfo(),
        writer = postWriter(),
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
        postId = POST_ID,
        writerId = POST_WRITER_ID
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

val pagedPostResponse: () -> PagedPostResponse = fun (): PagedPostResponse {
    return PagedPostResponse.of(pagedPosts())
}