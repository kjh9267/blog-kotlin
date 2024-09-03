package me.jun.common.security

import org.springframework.stereotype.Component

@Component
class MemberIdExtractor {

    fun extractMemberId(email: String?): Long? {
        return 1L
    }
}