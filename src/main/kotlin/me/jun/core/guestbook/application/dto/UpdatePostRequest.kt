package me.jun.core.guestbook.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import me.jun.core.guestbook.domain.Post
import me.jun.core.guestbook.domain.PostInfo

data class UpdatePostRequest(
    @field:NotNull
    @field:Positive
    var postId: Long,
    @field:NotBlank
    var newTitle: String,
    @field:NotBlank
    var newContent: String,
    var writerId: Long
) {

    fun toEntity(): Post {
        return Post(
            postId = postId,
            postInfo = PostInfo(
                title = newTitle,
                content = newContent
            ),
            writerId = writerId,
            createdAt = null,
            updatedAt = null
        )
    }
}
