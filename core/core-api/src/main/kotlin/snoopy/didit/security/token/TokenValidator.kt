package snoopy.didit.security.token

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Component
import snoopy.didit.auth.AuthRepository
import snoopy.didit.exception.BusinessException
import snoopy.didit.security.constants.JwtCode
import snoopy.didit.security.extensions.getUserId
import snoopy.didit.template.ErrorCode

@Component
class TokenValidator(
    private val repository: AuthRepository,
    private val keyProvider: KeyProvider,
) {
    fun validate(inputToken: Token): JwtCode {
        kotlin
            .runCatching {
                inputToken.getClaims(keyProvider.key)
                return JwtCode.ACCESS
            }.getOrElse { e ->
                return pickJwtCode(e)
            }
    }

    fun verifyToken(accessToken: Token) {
        // FIXME: 여기 깔끔하게 다시 리팩해야 함
        val claims = accessToken.getClaims(keyProvider.key)
        val userId = claims.getUserId()
        val result = repository.findAccessToken(userId)
        if (!result) {
            throw BusinessException(ErrorCode.LOGOUT_TOKEN)
        }
    }

    private fun pickJwtCode(e: Throwable) =
        when (e) {
            is SecurityException,
            is MalformedJwtException,
            is UnsupportedJwtException,
            is IllegalArgumentException,
            -> JwtCode.DENIED

            is ExpiredJwtException -> JwtCode.EXPIRED
            else -> JwtCode.DENIED
        }
}
