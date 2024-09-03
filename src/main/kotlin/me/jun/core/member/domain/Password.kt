package me.jun.core.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import me.jun.core.member.domain.exception.WrongPasswordException

@Embeddable
open class Password(
    @Column(
        name = "password",
        nullable = false
    )
    open var value: String
) {

    fun validate(value: String): Unit {
        if (this.value != value) {
            throw WrongPasswordException.of(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Password) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}