package com.example.ecommercebackend.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppJwtProperties::class)
class SecurityBeans