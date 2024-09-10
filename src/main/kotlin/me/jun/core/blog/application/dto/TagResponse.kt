package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Tag

data class TagResponse(
    var tagId: Long,
    var tagName: String
) {

    companion object {
        fun of(tag: Tag): TagResponse {
            return TagResponse(
                tagId = tag.tagId!!,
                tagName = tag.name
            )
        }
    }
}
