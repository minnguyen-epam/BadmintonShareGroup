package com.app.badmintonsharegroup.controller.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateUserAccountRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,

    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotBlank
    @field:Email
    val email: String,

    val phoneNumber: String?,

    @field:NotBlank
    @field:Size(min = 8, max = 255)
    val password: String,
)

data class UpdateUserAccountRequest(
    @field:Size(max = 100)
    val name: String?,

    @field:Email
    val email: String?,

    val phoneNumber: String?,
)

data class LoginRequest(
    @field:NotBlank
    val username: String,

    @field:NotBlank
    val password: String,
)

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val userId: UUID?,
    val username: String,
)
