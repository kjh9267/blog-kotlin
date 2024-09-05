package me.jun.core.guestbook.application.dto

import me.jun.core.guestbook.domain.Post

data class CreatePostRequest(
    var title: String,
    var content: String,
    var writerId: Long
) {

    fun toEntity(): Post {
        return Post(
            postId = null,
            title = title,
            content = content,
            writerId = writerId,
            createdAt = null,
            updatedAt = null
        )
    }
}
