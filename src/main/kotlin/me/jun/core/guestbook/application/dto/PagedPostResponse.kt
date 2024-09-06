package me.jun.core.guestbook.application.dto

import me.jun.core.guestbook.domain.Post
import org.springframework.data.domain.Page

data class PagedPostResponse(
    var pageResponses: Page<PostResponse>
) {

    companion object {
        fun of(posts: Page<Post>): PagedPostResponse {
            return PagedPostResponse(
                posts.map { PostResponse.of(it) }
            )
        }
    }
}
