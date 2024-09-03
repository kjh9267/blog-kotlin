package me.jun.common.security.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.UNAUTHORIZED

class InvalidTokenException private constructor(message: String?): BusinessException(message) {

    init {
        status = UNAUTHORIZED
    }

    companion object {
        fun of(message: String?): InvalidTokenException {
            return InvalidTokenException(message)
        }
    }
}