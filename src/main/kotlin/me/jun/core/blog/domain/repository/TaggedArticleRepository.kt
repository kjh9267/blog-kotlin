package me.jun.core.blog.domain.repository

import me.jun.core.blog.domain.TaggedArticle
import org.springframework.data.jpa.repository.JpaRepository

interface TaggedArticleRepository: JpaRepository<TaggedArticle, Long> {

    fun findByTaggedArticleId(taggedArticleId: Long?): TaggedArticle?
}
