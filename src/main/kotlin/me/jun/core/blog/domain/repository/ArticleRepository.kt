package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {
}