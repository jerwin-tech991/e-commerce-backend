package com.example.ecommercebackend.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AppUserRepository: JpaRepository<AppUser, Long> {
    fun findByEmail(email: String): Optional<AppUser>
    fun existsByEmail(email: String): Boolean
}