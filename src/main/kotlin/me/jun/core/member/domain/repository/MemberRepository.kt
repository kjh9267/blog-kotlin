package me.jun.core.member.domain.repository

import me.jun.core.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByMemberId(memberId: Long?): Member?
}
