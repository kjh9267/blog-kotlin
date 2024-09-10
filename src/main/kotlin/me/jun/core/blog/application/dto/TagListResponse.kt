package me.jun.core.blog.application.dto

import me.jun.core.blog.domain.Tag

data class TagListResponse(
    var tagResponses: List<TagResponse>
) {

    companion object {
        fun of(tags: List<Tag>): TagListResponse {
            return TagListResponse(
                tags.map { TagResponse.of(it) }
            )
        }
    }
}
