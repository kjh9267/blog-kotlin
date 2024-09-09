package me.jun.core.blog.domain

import jakarta.persistence.*

@Entity
open class TaggedArticle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var taggedArticleId: Long?,

    @Column(nullable = false)
    open var tagId: Long?,

    @Column(nullable = false)
    open var articleId: Long?
) {

    fun match(tagId: Long?, articleId: Long?): TaggedArticle {
        this.tagId = tagId
        this.articleId = articleId
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TaggedArticle) return false

        if (taggedArticleId != other.taggedArticleId) return false

        return true
    }

    override fun hashCode(): Int {
        return taggedArticleId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "TaggedArticle(taggedArticleId=$taggedArticleId, tagId=$tagId, articleId=$articleId)"
    }
}
