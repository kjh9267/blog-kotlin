package me.jun.core.member.domain

import me.jun.core.member.domain.exception.WrongPasswordException

open class Password(
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