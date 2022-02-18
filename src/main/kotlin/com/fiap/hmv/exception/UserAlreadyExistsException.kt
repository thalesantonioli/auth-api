package com.fiap.hmv.exception

data class UserAlreadyExistsException(val msg: String): RuntimeException(msg)
