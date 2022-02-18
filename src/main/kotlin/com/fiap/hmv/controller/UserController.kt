package com.fiap.hmv.controller

import com.fiap.hmv.model.AuthToken
import com.fiap.hmv.model.LoginRequest
import com.fiap.hmv.model.OTPRequest
import com.fiap.hmv.model.UserRequest
import com.fiap.hmv.model.persistence.User
import com.fiap.hmv.service.UserService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): Mono<User> {
        return userService.findUserById(UUID.fromString(userId))
    }

    @PostMapping("/register")
    @ResponseBody
    fun save(@RequestBody userRequest: UserRequest): Mono<User> {
        return userService.register(userRequest)
    }

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: String): Mono<Void> {
        return userService.deleteUserById(UUID.fromString(userId))
    }

}