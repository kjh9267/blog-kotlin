package me.jun.core.blog.domain

import jakarta.persistence.*

@Entity
open class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var categoryId: Long?,

    @Column(nullable = false)
    open var name: String,

    @Column(nullable = false)
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

    override fun toString(): String {
        return "Category(categoryId=$categoryId, name='$name', mappedArticleCount=$mappedArticleCount)"
    }
}
