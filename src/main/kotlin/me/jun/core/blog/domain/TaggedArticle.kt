package me.jun.core.blog.domain

open class TaggedArticle(
    open var taggedArticleId: Long?,
    open var tagId: Long?,
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
