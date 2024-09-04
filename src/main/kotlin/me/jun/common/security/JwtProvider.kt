package me.jun.common.security

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS512
import me.jun.common.security.exception.InvalidTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Suppress("Deprecation")
class JwtProvider(
    @Value("\${jwt-key}") val jwtKey: String
) {

    fun createToken(email: String?): String {
        return Jwts.builder()
            .setSubject(email)
            .signWith(HS512, jwtKey)
            .compact()
    }

    fun extractSubject(token: String?): String {
        try {
            return createParser()
                .parseClaimsJws(token)
                .body
                .subject
        }
        catch (e: Exception) {
            throw InvalidTokenException.of(e.message)
        }
    }

    private fun createParser(): JwtParser {
        return Jwts.parser()
            .setSigningKey(jwtKey)
            .build()
    }
}