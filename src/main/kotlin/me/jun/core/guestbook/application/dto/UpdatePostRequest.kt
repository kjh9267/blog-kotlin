package me.jun.core.guestbook.application.dto

import me.jun.core.guestbook.domain.Post

data class UpdatePostRequest(
    var postId: Long,
    var newTitle: String,
    var newContent: String,
    var writerId: Long
) {

    fun toEntity(): Post {
        return Post(
            postId = postId,
            title = newTitle,
            content = newContent,
            writerId = writerId,
            createdAt = null,
            updatedAt = null
        )
    }
}
