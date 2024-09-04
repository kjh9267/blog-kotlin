package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {

    fun findByName(name: String?): Category?
}
