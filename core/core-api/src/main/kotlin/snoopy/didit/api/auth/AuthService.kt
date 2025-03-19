package snoopy.didit.api.auth

import getOrThrow
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import snoopy.didit.auth.AuthRepository
import snoopy.didit.domain.member.Member
import snoopy.didit.exception.BusinessException
import snoopy.didit.security.constants.JwtCode
import snoopy.didit.security.extensions.getUserId
import snoopy.didit.security.token.RefreshToken
import snoopy.didit.security.token.TokenGenerator
import snoopy.didit.security.token.TokenResponse
import snoopy.didit.security.token.TokenValidator
import snoopy.didit.template.ErrorCode

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenGenerator: TokenGenerator,
    private val tokenValidator: TokenValidator,
    private val repository: AuthRepository,
) {
    fun emailLogin(request: LoginRequest): TokenResponse {
        val authentication: Authentication = getAuthentication(request.email, request.password)
        val tokenResponse = createTokenWithAuthentication(authentication)
        registerTokensInWhiteList(tokenResponse, extractUserId(authentication))
        return tokenResponse
    }

    fun socialLogin(member: Member): TokenResponse {
        val tokenResponse = createTokenWithMember(member)
        registerTokensInWhiteList(tokenResponse, member.id.getOrThrow())
        return tokenResponse
    }

    fun logout(userId: Long): Boolean {
        val response = repository.deleteTokens(userId)
        return response
    }

    fun reIssue(request: ReIssueRequest): TokenResponse {
        val requestRefreshToken = RefreshToken.of(request.refreshToken)
        val userId = request.userId

        val jwtCode = tokenValidator.validate(requestRefreshToken)
        val isExistToken = repository.findRefreshToken(userId)
        validateRefreshTokenStatus(jwtCode, isExistToken)

        val generatedTokens = tokenGenerator.reIssueBothToken(userId = userId, authorityList = listOf("ORDER_READ"))
        val tokenResponse = TokenResponse.from(generatedTokens)
        registerTokensInWhiteList(tokenResponse, userId)

        return tokenResponse
    }

    private fun getAuthentication(
        email: String,
        password: String,
    ): Authentication {
        val authenticationToken = UsernamePasswordAuthenticationToken(email, password)
        return createAuthentication(authenticationToken)
    }

    private fun createAuthentication(token: UsernamePasswordAuthenticationToken): Authentication {
        try {
            return authenticationManagerBuilder.`object`.authenticate(token)
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.INVALID_LOGIN_INFO)
        }
    }

    internal fun createTokenWithAuthentication(authentication: Authentication): TokenResponse =
        TokenResponse(
            tokenGenerator.createAccessToken(authentication),
            tokenGenerator.createRefreshToken(authentication),
        )

    internal fun createTokenWithMember(member: Member): TokenResponse =
        TokenResponse(
            tokenGenerator.createAccessTokenWithMember(member),
            tokenGenerator.createRefreshTokenWithMember(member),
        )

    private fun extractUserId(authentication: Authentication): Long = authentication.getUserId()

    private fun registerTokensInWhiteList(
        tokenResponse: TokenResponse,
        userId: Long,
    ) {
        repository.addTokens(
            refreshToken = tokenResponse.refreshToken,
            accessToken = tokenResponse.accessToken,
            userId = userId,
        )
    }

    private fun validateRefreshTokenStatus(
        jwtCode: JwtCode,
        isExistToken: Boolean,
    ) {
        if (jwtCode != JwtCode.ACCESS || !isExistToken) {
            throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        }
    }
}
