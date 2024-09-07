package me.jun.core.guestbook.domain

import jakarta.persistence.*

@Entity
open class PostCount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var postCountId: Long?,

    @Column(name = "hits")
    open var value: Long,

    @Column
    open var postId: Long,

    @Version
    open var version: Long
) {

    fun increment(): PostCount {
        this.value += 1
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PostCount) return false

        if (postCountId != other.postCountId) return false

        return true
    }

    override fun hashCode(): Int {
        return postCountId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "PostCount(postCountId=$postCountId, value=$value, postId=$postId, version=$version)"
    }
}
