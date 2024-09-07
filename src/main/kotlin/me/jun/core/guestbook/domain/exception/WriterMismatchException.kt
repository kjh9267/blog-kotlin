package me.jun.core.guestbook.domain.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class WriterMismatchException private constructor(message: String): BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String): WriterMismatchException {
            return WriterMismatchException(message)
        }
    }
}