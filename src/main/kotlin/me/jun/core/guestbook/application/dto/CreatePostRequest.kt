package me.jun.core.guestbook.application.dto

import jakarta.validation.constraints.NotBlank
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.PostInfo
import me.jun.core.guestbook.domain.Writer

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
            postInfo = PostInfo(
                title = title,
                content = content
            ),
            writer = Writer(writerId),
            createdAt = null,
            updatedAt = null
        )
    }
}
