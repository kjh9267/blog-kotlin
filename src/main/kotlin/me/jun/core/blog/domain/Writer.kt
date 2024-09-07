package me.jun.core.blog.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class Writer(
    @Column(name = "writerId")
    open var value: Long,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Writer) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Writer(value=$value)"
    }
}
