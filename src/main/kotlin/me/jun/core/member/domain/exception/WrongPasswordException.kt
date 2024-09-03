package me.jun.core.member.domain.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.UNAUTHORIZED

class WrongPasswordException private constructor(message: String) : BusinessException(message) {

    init {
        status = UNAUTHORIZED
    }

    companion object {
        fun of(message: String): WrongPasswordException {
            return WrongPasswordException(message)
        }
    }
}
