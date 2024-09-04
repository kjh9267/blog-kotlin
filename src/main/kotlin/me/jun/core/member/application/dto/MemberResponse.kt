package me.jun.core.member.application.dto

import me.jun.core.member.domain.Member
import me.jun.core.member.domain.Role
import me.jun.core.member.domain.repository.MemberRepository
import java.time.Instant

data class MemberResponse(
    var memberId: Long,
    var name: String,
    var email: String,
    var role: Role,
    var createdAt: Instant,
    var updatedAt: Instant
) {

    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(
                memberId = member.memberId!!,
                name = member.name,
                email = member.email,
                role = member.role,
                createdAt = member.createdAt!!,
                updatedAt = member.updatedAt!!
            )
        }
    }
}
