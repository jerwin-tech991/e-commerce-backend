package com.example.ecommercebackend.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.EnumType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetTime

@Entity
@Table(name = "app_users")
class AppUser(email: String, passwordHash: String?, role: UserRole) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String = ""

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole = UserRole.BUYER

    @Column(name = "status", nullable = false)
    var status: String = "ACTIVE"

    @Column(name = "created_at", nullable = false)
    var createdAt: OffsetTime = OffsetTime.now()
}