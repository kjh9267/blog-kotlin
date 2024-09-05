package me.jun.core.guestbook.application.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class PostNotFoundException private constructor(message: String): BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String): PostNotFoundException {
            return PostNotFoundException(message)
        }
    }
}
