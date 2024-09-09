package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {

    fun findByTagId(tagId: Long?): Tag?
}
