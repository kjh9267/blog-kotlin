package me.jun.core.blog.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class ArticleInfo(
    @Column(nullable = false)
    open var title: String,

    @Column(nullable = false)
    open var content: String
) {

    fun updateTitle(newTitle: String?): ArticleInfo {
        this.title = newTitle!!
        return this;
    }

    fun updateContent(newContent: String?): ArticleInfo {
        this.content = newContent!!
        return this;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArticleInfo

        if (title != other.title) return false
        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}
