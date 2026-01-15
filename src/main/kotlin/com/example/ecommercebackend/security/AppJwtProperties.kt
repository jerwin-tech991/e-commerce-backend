package com.example.ecommercebackend.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.jwt")
data class AppJwtProperties(
    val secret: String = "",
    val issuer: String = "",
    val ttlMinutes: Long = 12345
)