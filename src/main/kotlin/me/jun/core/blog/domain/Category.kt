package me.jun.core.blog.domain

open class Category(
    open var categoryId: Long?,
    open var name: String,
    open var mappedArticleCount: Long
) {

    fun incrementMappedArticleCount(): Category {
        this.mappedArticleCount += 1
        return this
    }

    fun decrementMappedArticleCount(): Category {
        this.mappedArticleCount -= 1;
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false

        if (categoryId != other.categoryId) return false

        return true
    }

    override fun hashCode(): Int {
        return categoryId?.hashCode() ?: 0
    }
}
