package com.fiap.hmv.exception

data class UserNotFoundException(val msg: String): RuntimeException(msg)
