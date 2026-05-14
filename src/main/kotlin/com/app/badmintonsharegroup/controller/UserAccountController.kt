package com.app.badmintonsharegroup.controller

import com.app.badmintonsharegroup.controller.dto.AuthResponse
import com.app.badmintonsharegroup.controller.dto.CreateUserAccountRequest
import com.app.badmintonsharegroup.controller.dto.LoginRequest
import com.app.badmintonsharegroup.dto.response.UserAccountResponse
import com.app.badmintonsharegroup.service.UserAccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/auth")
class UserAccountController(private val userAccountService: UserAccountService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: CreateUserAccountRequest): Mono<UserAccountResponse> =
        userAccountService.register(request)

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): Mono<AuthResponse> = userAccountService.login(request)
}
