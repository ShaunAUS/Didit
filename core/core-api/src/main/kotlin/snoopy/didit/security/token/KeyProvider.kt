package snoopy.didit.security.token

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class KeyProvider
    @Autowired
    constructor(
        @Value("\${jwt.secret}") private val secretKey: String,
    ) {
        val key: Key by lazy { Keys.hmacShaKeyFor(secretKey.toByteArray()) }
    }
