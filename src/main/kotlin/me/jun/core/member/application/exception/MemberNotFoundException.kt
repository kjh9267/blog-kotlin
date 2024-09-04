package me.jun.core.member.application.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class MemberNotFoundException private constructor(message: String) : BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String): MemberNotFoundException {
            return MemberNotFoundException(message)
        }
    }
}
