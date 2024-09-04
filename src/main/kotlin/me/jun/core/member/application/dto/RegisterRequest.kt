package me.jun.core.member.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import me.jun.core.member.domain.Member
import me.jun.core.member.domain.Password
import me.jun.core.member.domain.Role

data class RegisterRequest(
    @field:NotBlank
    @field:Email
    var email: String,
    @field:NotBlank
    var name: String,
    @field:NotBlank
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