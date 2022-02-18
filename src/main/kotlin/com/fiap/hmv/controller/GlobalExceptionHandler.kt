package com.fiap.hmv.controller

import com.fiap.hmv.exception.InvalidAuthenticationException
import com.fiap.hmv.exception.UserAlreadyExistsException
import com.fiap.hmv.exception.UserNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler {
    @ExceptionHandler(value = [InvalidAuthenticationException::class])
    protected fun handleUnauthorized(
        ex: Exception
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    protected fun handleNonexistentUser(
        ex: Exception
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    protected fun handleExistentUser(
        ex: Exception
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun unexpectedError(
        ex: Exception
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}