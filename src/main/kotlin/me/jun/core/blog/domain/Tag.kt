package me.jun.core.blog.domain

import jakarta.persistence.*

@Entity
open class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var tagId: Long?,

    @Column(nullable = false)
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
