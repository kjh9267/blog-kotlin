package me.jun.core.blog.application.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class CategoryNotFoundException(message: String): BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String): CategoryNotFoundException {
            return CategoryNotFoundException(message)
        }
    }
}