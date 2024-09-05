package me.jun.core.guestbook.application.dto

import jakarta.validation.constraints.NotBlank
import me.jun.core.guestbook.domain.Post

data class CreatePostRequest(
    @field:NotBlank
    var title: String,
    @field:NotBlank
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
