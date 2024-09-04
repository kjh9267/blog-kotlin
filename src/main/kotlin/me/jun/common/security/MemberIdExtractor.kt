package me.jun.common.security

import me.jun.core.member.application.MemberService
import me.jun.core.member.application.dto.RetrieveMemberRequest
import org.springframework.stereotype.Component

@Component
class MemberIdExtractor(
    private val memberService: MemberService
) {

    fun extractMemberId(email: String?): Long? {
        val request: RetrieveMemberRequest = RetrieveMemberRequest(email!!)
        val memberResponse = memberService.retrieveMember(request)
        return memberResponse.memberId
    }
}