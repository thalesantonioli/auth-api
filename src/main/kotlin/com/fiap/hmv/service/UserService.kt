package com.fiap.hmv.service

import com.fiap.hmv.exception.UserAlreadyExistsException
import com.fiap.hmv.exception.UserNotFoundException
import com.fiap.hmv.model.*
import com.fiap.hmv.model.persistence.User
import com.fiap.hmv.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserService(private val repository: UserRepository) {

    fun register(request: UserRequest): Mono<User> {
        return repository.save(request.toUser()).doOnError {
            throw UserAlreadyExistsException("Email ${request.email} already exists in the database")
        }}

    fun findUserById(userId: UUID): Mono<User> = repository.findById(userId)

    fun findUserByEmail(email: String) = repository.findByEmail(email)

    fun deleteUserById(userId: UUID): Mono<Void> = repository.deleteById(userId)


}