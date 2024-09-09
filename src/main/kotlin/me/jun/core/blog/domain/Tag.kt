package me.jun.core.blog.domain

open class Tag(
    open var tag_id: Long?,
    open var name: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tag) return false

        if (tag_id != other.tag_id) return false

        return true
    }

    override fun hashCode(): Int {
        return tag_id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Tag(id=$tag_id, name='$name')"
    }
}
