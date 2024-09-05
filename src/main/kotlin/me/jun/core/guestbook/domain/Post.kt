package me.jun.core.guestbook.domain

import java.time.Instant

open class Post(
    open var postId: Long?,
    open var title: String,
    open var content: String,
    open var writerId: Long,
    open var createdAt: Instant,
    open var updatedAt: Instant
) {

}
