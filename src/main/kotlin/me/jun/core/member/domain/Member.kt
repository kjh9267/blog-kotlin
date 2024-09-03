package me.jun.core.member.domain

import java.time.Instant

open class Member(
    open var memberId: Long?,
    open var name: String,
    open var email: String,
    open var password: Password,
    open var role: Role,
    open var createdAt: Instant,
    open var updatedAt: Instant
) {

    fun validatePassword(password: String) {
        this.password.validate(password)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        if (memberId != other.memberId) return false

        return true
    }

    override fun hashCode(): Int {
        return memberId?.hashCode() ?: 0
    }
}