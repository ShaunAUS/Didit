package snoopy.didit.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import snoopy.didit.exception.BusinessException
import snoopy.didit.security.config.PermitAllPathProvider
import snoopy.didit.security.constants.JwtCode
import snoopy.didit.security.token.AccessToken
import snoopy.didit.security.token.Token
import snoopy.didit.security.token.TokenExtractor
import snoopy.didit.security.token.TokenValidator
import snoopy.didit.template.ErrorCode

class JwtFilter(
    private val tokenValidator: TokenValidator,
    private val tokenExtractor: TokenExtractor,
    private val permitAllPathProvider: PermitAllPathProvider,
) : OncePerRequestFilter() {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
        private const val SPLIT_BEARER_SIZE = 7
        private val pathMatcher = AntPathMatcher()
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return permitAllPathProvider.publicPaths.any {
            pathMatcher.match(it, path)
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val accessToken = resolveToken(request)
        isActiveToken(accessToken)
        extractToken(accessToken)

        filterChain.doFilter(request, response)
    }

    fun extractToken(accessToken: Token) {
        when (validateToken(accessToken)) {
            JwtCode.ACCESS -> setSecurityContextHolder(accessToken as AccessToken)
            JwtCode.EXPIRED -> {
                throw BusinessException(ErrorCode.EXPIRED_TOKEN)
            }

            JwtCode.DENIED -> {
                throw BusinessException(ErrorCode.ACCESS_DENIED)
            }
        }
    }

    fun resolveToken(request: HttpServletRequest): Token {
        val token: String = request.getHeader(AUTHORIZATION_HEADER).orEmpty()

        if (token.isBlank()) {
            throw BusinessException(ErrorCode.NOT_FOUND_TOKEN)
        }

        if (!token.startsWith(BEARER)) {
            throw BusinessException(ErrorCode.INVALID_START_TOKEN)
        }

        return AccessToken.of(token.substring(SPLIT_BEARER_SIZE))
    }

    private fun isActiveToken(accessToken: Token) = tokenValidator.verifyToken(accessToken)

    private fun validateToken(accessToken: Token): JwtCode = tokenValidator.validate(accessToken)

    private fun setSecurityContextHolder(accessToken: AccessToken) {
        val authentication = tokenExtractor.extractJwtAuthentication(accessToken)
        SecurityContextHolder.getContext().authentication = authentication
    }
}
