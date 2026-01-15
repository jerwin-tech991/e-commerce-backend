package com.example.ecommercebackend.auth

import com.example.ecommercebackend.security.AppJwtProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthService(
    private val users: AppUserRepository,
    private val encoder: PasswordEncoder,
    private val jwtEncoder: JwtEncoder,
    private val jwtProps: AppJwtProperties
) {
    fun register(req: RegisterRequest): String {
        if (users.existsByEmail(req.email.lowercase())) throw IllegalArgumentException("Email already exists")

        val role = req.role ?: UserRole.BUYER
        val user = AppUser(
            email = req.email.lowercase(),
            passwordHash = encoder.encode(req.password),
            role = role
        )
        val saved = users.save(user)

        return issueToken(saved)
    }

    fun login(req: LoginRequest): String {
        val user = users.findByEmail(req.email.lowercase())
            .orElseThrow { IllegalArgumentException("User not found") }

        if (!encoder.matches(req.password, user.passwordHash)) {
            throw IllegalArgumentException("Invalid password")
        }

        if (user.status != "ACTIVE") {
            throw IllegalArgumentException("User is not active")
        }

        return issueToken(user)
    }

    private fun issueToken(user: AppUser): String {
        val now = Instant.now()
        val exp = now.plusSeconds(jwtProps.ttlMinutes * 60)
        val claims = JwtClaimsSet.builder()
            .issuer(jwtProps.issuer)
            .issuedAt(now)
            .expiresAt(exp)
            .subject(user.id!!.toString())
            .claim("email", user.email)
            .claim("role", user.role.name)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
