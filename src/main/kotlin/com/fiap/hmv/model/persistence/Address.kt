package com.fiap.hmv.model.persistence

import org.springframework.data.mongodb.core.mapping.Document

@Document
class Address(
    val streetNumber: String?,
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val country: String
)