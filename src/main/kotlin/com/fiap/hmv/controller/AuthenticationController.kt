package com.fiap.hmv.controller

import com.fiap.hmv.model.AuthToken
import com.fiap.hmv.model.LoginRequest
import com.fiap.hmv.model.OTPRequest
import com.fiap.hmv.model.ValidateTokenRequest
import com.fiap.hmv.service.AuthenticationService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/authenticate")
    @ResponseBody
    suspend fun login(@RequestBody request: LoginRequest) {
        authenticationService.login(request)
    }

    @PostMapping("/otp-validation")
    @ResponseBody
    suspend fun otpLogin(@RequestBody request: OTPRequest): AuthToken {
        return authenticationService.validateOTP(request).awaitFirst()
    }

    @PostMapping("/validate-token")
    suspend fun validate(@RequestBody request: ValidateTokenRequest) {
        authenticationService.validateToken(request)
    }
}