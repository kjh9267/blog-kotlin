package me.jun.support

import org.springframework.http.HttpStatusCode

abstract class BusinessException(message: String?) : RuntimeException(message) {

    protected lateinit var status: HttpStatusCode
}