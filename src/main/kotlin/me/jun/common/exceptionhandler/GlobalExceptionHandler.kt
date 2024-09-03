package me.jun.common.exceptionhandler

import me.jun.support.BusinessException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun businessExceptionHandler(e: BusinessException): ErrorResponse {
        return ErrorResponse.builder(e, e.status, e.message!!)
            .build()
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun bindExceptionHandler(e: Exception): ErrorResponse {
        return ErrorResponse.builder(e, BAD_REQUEST, e.message!!)
            .build()
    }

    @ExceptionHandler(Exception::class)
    fun businessExceptionHandler(e: Exception): ErrorResponse {
        return ErrorResponse.builder(e, INTERNAL_SERVER_ERROR, e.message!!)
            .build()
    }
}