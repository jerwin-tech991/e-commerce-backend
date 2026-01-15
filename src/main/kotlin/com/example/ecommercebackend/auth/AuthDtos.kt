package com.example.ecommercebackend.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:Email val email: String,
    @field:NotBlank val password: String,
    val role: UserRole? = null
)

data class LoginRequest(
    @field:Email val email: String,
    @field:NotBlank val password: String
)

data class AuthResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)