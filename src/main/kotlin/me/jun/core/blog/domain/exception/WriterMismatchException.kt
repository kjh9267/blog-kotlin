package me.jun.core.blog.domain.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.UNAUTHORIZED

class WriterMismatchException private constructor(message: String): BusinessException(message) {

    init {
        status = UNAUTHORIZED
    }

    companion object {
        fun of(message: String): WriterMismatchException {
            return WriterMismatchException(message)
        }
    }
}