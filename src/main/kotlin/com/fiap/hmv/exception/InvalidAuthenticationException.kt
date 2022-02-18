package com.fiap.hmv.exception

data class InvalidAuthenticationException(val msg: String): RuntimeException(msg)
