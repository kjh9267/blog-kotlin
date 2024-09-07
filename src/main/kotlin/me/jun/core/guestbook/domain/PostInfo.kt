package me.jun.core.guestbook.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class PostInfo(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String
) {

    fun updateTitle(newTitle: String?): PostInfo {
        this.title = newTitle!!
        return this
    }

    fun updateContent(newContent: String?): PostInfo {
        this.content = newContent!!
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostInfo) return false

        if (title != other.title) return false
        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }

    override fun toString(): String {
        return "PostInfo(title='$title', content='$content')"
    }
}
