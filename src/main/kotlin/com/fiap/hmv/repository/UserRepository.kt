package com.fiap.hmv.repository

import com.fiap.hmv.model.persistence.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface UserRepository : ReactiveMongoRepository<User, UUID> {
    fun findByEmail(email: String): Mono<User>
}
