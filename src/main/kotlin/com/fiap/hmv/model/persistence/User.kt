package com.fiap.hmv.model.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("User")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @Indexed(unique=true)
    val email: String,
    val cellphone: String?,
    val documentIdentification: String,
    val birthday: Date,
    val address: Address?,
)