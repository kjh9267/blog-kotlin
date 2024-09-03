package me.jun.support

import me.jun.core.member.domain.Member
import me.jun.core.member.domain.Password
import me.jun.core.member.domain.Role
import java.time.Instant
import java.time.Instant.now

const val MEMBER_ID: Long = 1L;

const val MEMBER_NAME: String = "member name string"

const val MEMBER_EMAIL: String = "asdf@asdf.com"

const val PASSWORD: String = "password string"

val MEMBER_CREATED_AT: Instant = now()

val MEMBER_UPDATED_AT: Instant = now()

val admin: () -> Member = fun(): Member {
    return Member(
        memberId = MEMBER_ID,
        name = MEMBER_NAME,
        email = MEMBER_EMAIL,
        password = password(),
        role = Role.ADMIN,
        createdAt = MEMBER_CREATED_AT,
        updatedAt = MEMBER_UPDATED_AT
    )
}

val user: () -> Member = fun(): Member {
    return Member(
        memberId = MEMBER_ID,
        name = MEMBER_NAME,
        email = MEMBER_EMAIL,
        password = password(),
        role = Role.USER,
        createdAt = MEMBER_CREATED_AT,
        updatedAt = MEMBER_UPDATED_AT
    )
}

val password: () -> Password = fun (): Password {
    return Password(PASSWORD)
}