package com.fiap.hmv.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fiap.hmv.exception.InvalidAuthenticationException
import com.fiap.hmv.exception.UserNotFoundException
import com.fiap.hmv.model.*
import com.fiap.hmv.model.persistence.User
import com.fiap.hmv.publisher.SQSPublisher
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class AuthenticationService(
    private val publisher: SQSPublisher,
    @Qualifier("otp-cache") private val cacheOTP: Cache,
    @Qualifier("active-tokens") private val cacheToken: Cache,
    private val mapper: ObjectMapper,
    private val userService: UserService
) {

    @Value("\${cloud.aws.otp.queue}")
    lateinit var otpQueue: String

    suspend fun login(request: LoginRequest) {
        val user = userService.findUserByEmail(request.email)
        user.doOnSuccess {
            sendOTPMessage(it)
        }.awaitFirst()
    }

    suspend fun validateOTP(request: OTPRequest): Mono<AuthToken> {
        val user = userService.findUserByEmail(request.email).awaitFirstOrElse {
            throw UserNotFoundException("User not found for email: ${request.email}")
        }
        return Mono.just(authenticate(user, request.code))
    }

    fun validateToken(request: ValidateTokenRequest) {
        val savedToken = cacheToken.get(request.userId)
        if (savedToken == null || savedToken.get()?.toString() != request.token) {
            throw InvalidAuthenticationException("Invalid authentication parameters")
        }
    }

    private fun sendOTPMessage(user: User) {
        val oneTimePassword = generateOTP()
        println(oneTimePassword)
        cacheOTP.put(user.email, oneTimePassword)

        val subject = "HMV Challenge -> Autenticaçaão OTP"
        val bodyMessage = "Olá, ${user.name}. seu coódigo de autenticação é: $oneTimePassword"
        val receiver = listOf(user.email)
        val message = OTPMessage(toEmails = receiver, message = bodyMessage, subject = subject)
        publisher.publish(otpQueue, mapper.writeValueAsString(message))
    }

    private fun generateOTP(): String {
        val rnd = Random()
        val number: Int = rnd.nextInt(999999)
        return String.format("%06d", number)
    }

    private fun authenticate(user: User, code: String): AuthToken {
        val result = cacheOTP.get(user.email)
        result?.let {
            if (result.get().toString() == code) {
                val authToken = generateToken(user.id)
                cacheToken.put(user.id, authToken.token)
                return authToken
            }
        }
        throw InvalidAuthenticationException("Invalid login credentials")
    }

    private fun generateToken(userId: UUID): AuthToken {
        val signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)
        val jwt = Jwts.builder().setSubject(userId.toString()).signWith(signingKey).compact()
        println("exampleJwt: $jwt")
        return AuthToken(jwt)
    }
}