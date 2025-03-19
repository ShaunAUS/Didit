package snoopy.didit.security.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.security.Key

abstract class Token(
    val value: String,
) {
    init {
        validateValue(value)
    }

    fun getClaims(key: Key): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(this.value)
            .body

    private fun validateValue(value: String) {
        if (value.isBlank()) {
            throw IllegalArgumentException("Token content cannot be empty")
        }
    }
}
