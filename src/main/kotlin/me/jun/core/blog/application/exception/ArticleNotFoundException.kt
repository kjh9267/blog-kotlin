package me.jun.core.blog.application.exception

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.NOT_FOUND

class ArticleNotFoundException private constructor(message: String?): BusinessException(message) {

    init {
        status = NOT_FOUND
    }

    companion object {
        fun of(message: String?): ArticleNotFoundException {
            return ArticleNotFoundException(message)
        }
    }
}