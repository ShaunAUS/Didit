package snoopy.didit.security.token

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import snoopy.didit.api.auth.CustomUserDetails
import snoopy.didit.exception.BusinessException
import snoopy.didit.security.extensions.getAuthorities
import snoopy.didit.security.extensions.getUserId
import snoopy.didit.template.ErrorCode

@Component
class TokenExtractor(
    private val keyProvider: KeyProvider,
) {
    companion object {
        private const val EMPTY_CREDENTIAL = ""
    }

    fun extractJwtAuthentication(token: AccessToken): Authentication? {
        try {
            val claims = token.getClaims(keyProvider.key)
            val authorities = claims.getAuthorities()
            val userId = claims.getUserId()
            val principal = createCustomUserDetails(userId, authorities)
            return UsernamePasswordAuthenticationToken(principal, EMPTY_CREDENTIAL, authorities)
        } catch (e: ExpiredJwtException) {
            throw BusinessException(ErrorCode.EXPIRED_TOKEN)
        } catch (e: SignatureException) {
            throw BusinessException(ErrorCode.INVALID_SIGNATURE)
        } catch (e: MalformedJwtException) {
            throw BusinessException(ErrorCode.INVALID_TOKEN)
        } catch (e: JwtException) {
            throw BusinessException(ErrorCode.INVALID_TOKEN)
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.INVALID_TOKEN)
        }
    }

    private fun createCustomUserDetails(
        userId: Long,
        authorities: List<SimpleGrantedAuthority>,
    ) = CustomUserDetails(
        userId = userId,
        authorities = authorities,
    )
}
