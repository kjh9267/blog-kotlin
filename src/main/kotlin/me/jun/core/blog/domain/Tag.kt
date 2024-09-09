package me.jun.core.blog.domain

open class Tag(
    open var tagId: Long?,
    open var name: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tag) return false

        if (tagId != other.tagId) return false

        return true
    }

    override fun hashCode(): Int {
        return tagId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Tag(id=$tagId, name='$name')"
    }
}
