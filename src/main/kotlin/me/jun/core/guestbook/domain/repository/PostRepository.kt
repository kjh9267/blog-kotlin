package me.jun.core.guestbook.domain.repository

import me.jun.core.guestbook.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {

    fun findByPostId(postId: Long?): Post?

    fun findAllBy(pageable: Pageable?): Page<Post>
}