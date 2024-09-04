package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {

    fun findByArticleId(articleId: Long?): Article?

    fun findAllByCategoryId(categoryId: Long?, pageable: Pageable): Page<Article>
}