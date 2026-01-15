package com.example.ecommercebackend.security

import com.nimbusds.jose.jwk.source.ImmutableSecret
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoderFactory
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun jwtDecoder(props: AppJwtProperties): JwtDecoder {
        val key = SecretKeySpec(props.secret.toByteArray(Charsets.UTF_8), "HmacSHA256")

        return NimbusJwtDecoder.withSecretKey(key).build()
    }

    @Bean
    fun jwtEncoder(props: AppJwtProperties): JwtEncoder {
        val secret = props.secret.toByteArray(Charsets.UTF_8)

        return NimbusJwtEncoder(ImmutableSecret(secret))
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/actuator/**"
                    ).permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt { } }

        return http.build()
    }
}