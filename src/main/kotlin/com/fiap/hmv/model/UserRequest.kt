package com.fiap.hmv.model

import com.fiap.hmv.model.persistence.Address
import com.fiap.hmv.model.persistence.User
import java.util.*

class UserRequest(
    val name: String,
    val email: String,
    val cellphone: String?,
    val address: Address?,
    val documentIdentification: String,
    val birthday: Date
)


fun UserRequest.toUser() =
    User(
        name = this.name,
        email = this.email,
        cellphone = this.cellphone,
        address = this.address,
        documentIdentification = documentIdentification,
        birthday = birthday
    )