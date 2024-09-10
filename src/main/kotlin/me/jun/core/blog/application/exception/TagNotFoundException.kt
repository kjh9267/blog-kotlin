package me.jun.core.blog.application.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class TagNotFoundException private constructor(message: String) : BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String): TagNotFoundException {
            return TagNotFoundException(message)
        }
    }
}
