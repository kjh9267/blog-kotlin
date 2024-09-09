package me.jun.support

import me.jun.core.blog.domain.Tag

const val TAG_ID: Long = 1L

const val TAG_NAME: String = "tag name"

val tag: () -> Tag = fun(): Tag {
    return Tag(
        tag_id = TAG_ID,
        name = TAG_NAME
    )
}