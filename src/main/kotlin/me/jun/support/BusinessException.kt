package me.jun.support

import org.springframework.http.HttpStatusCode

abstract class BusinessException(message: String?) : RuntimeException(message) {

    lateinit var status: HttpStatusCode
}