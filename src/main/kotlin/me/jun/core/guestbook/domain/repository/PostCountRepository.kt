package me.jun.core.guestbook.domain.repository

import me.jun.core.guestbook.domain.PostCount
import org.springframework.data.jpa.repository.JpaRepository

interface PostCountRepository: JpaRepository<PostCount, Long> {

    fun findByPostCountId(postCountId: Long?): PostCount?
}
