package me.jun.core.guestbook.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import me.jun.core.guestbook.domain.exception.WriterMismatchException

@Embeddable
open class Writer(
    @Column(
        name = "writerId",
        nullable = false
    )
    open var value: Long
) {

    fun validate(value: Long) {
        if (this.value != value) {
            throw WriterMismatchException.of(value.toString())
        }
    }

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
