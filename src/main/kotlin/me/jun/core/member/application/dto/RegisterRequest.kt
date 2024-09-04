package me.jun.core.member.application.dto

import me.jun.core.member.domain.Member
import me.jun.core.member.domain.Password
import me.jun.core.member.domain.Role

data class RegisterRequest(
    var email: String,
    var name: String,
    var password: String
) {

    val role: Role = Role.USER

    fun toEntity(): Member {
        return Member(
            memberId = null,
            email = email,
            name = name,
            password = Password(password),
            role = role,
            createdAt = null,
            updatedAt = null
        )
    }
}