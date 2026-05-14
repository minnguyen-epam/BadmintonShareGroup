package com.app.badmintonsharegroup.security

import com.app.badmintonsharegroup.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(private val jwtProperties: JwtProperties) {

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))
    }

    fun generateToken(username: String): String = Jwts.builder()
        .subject(username)
        .issuedAt(Date())
        .expiration(Date(System.currentTimeMillis() + jwtProperties.expirationMs))
        .signWith(secretKey)
        .compact()

    fun extractUsername(token: String): String? = runCatching {
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }.getOrNull()

    fun isTokenValid(token: String): Boolean = runCatching {
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
        true
    }.getOrDefault(false)
}
