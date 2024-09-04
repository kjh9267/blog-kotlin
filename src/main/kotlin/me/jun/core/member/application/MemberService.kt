package me.jun.core.member.application

import me.jun.common.security.JwtProvider
import me.jun.core.member.application.dto.MemberResponse
import me.jun.core.member.application.dto.RegisterRequest
import me.jun.core.member.domain.Member
import me.jun.core.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider
) {

    fun register(request: RegisterRequest): MemberResponse {
        val member: Member = request.toEntity()
        val savedMember = memberRepository.save(member)
        return MemberResponse.of(savedMember)
    }
}
