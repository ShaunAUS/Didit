package snoopy.didit.security.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import snoopy.didit.domain.member.Member
import snoopy.didit.security.config.TokenExpireProvider
import snoopy.didit.security.extensions.getUserId
import snoopy.didit.security.extensions.toMillis
import java.util.Date

private const val ACCESS_TOKEN_SUBJECT = "ACCESS_TOKEN"
private const val REFRESH_TOKEN_SUBJECT = "REFRESH TOKEN"
private const val USER_PK = "up"
private const val PERMISSION = "role"

@Component
class TokenGenerator(private val tokenExpireProvider: TokenExpireProvider, private val keyProvider: KeyProvider) {
    fun createAccessToken(authentication: Authentication): AccessToken {
        // TODO: 권한 매핑부분 개발되어야 함
        val authorities: String =
            authentication
                .authorities
                .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.accessExpireTime.toMillis())
        val userId = authentication.getUserId()

        return AccessToken.of(
            Jwts
                .builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, userId)
                .claim(PERMISSION, authorities)
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }

    fun createAccessTokenWithMember(member: Member): AccessToken {
        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.accessExpireTime.toMillis())
        val userId = member.id

        return AccessToken.of(
            Jwts
                .builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, userId)
                // TODO 권한
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }

    fun reIssueBothToken(
        userId: Long,
        authorityList: List<String>,
    ): ReIssueTokenResponse =
        ReIssueTokenResponse(
            createAccessToken(userId, authorityList),
            createRefreshToken(userId),
        )

    fun createDriverAccessToken(
        driverId: Long,
        authorityList: List<String>,
    ): AccessToken =
        createAccessToken(
            driverId,
            authorityList,
        )

    fun createDriverRefreshToken(driverId: Long): RefreshToken = createRefreshToken(driverId)

    fun createRefreshToken(authentication: Authentication): RefreshToken {
        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.refreshExpireTime.toMillis())

        return RefreshToken.of(
            Jwts
                .builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, authentication.getUserId())
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }

    private fun createAccessToken(
        userId: Long,
        authorityList: List<String>,
    ): AccessToken {
        // TODO: 권한 매핑부분 개발되어야 함
        val authorities: String = authorityList.joinToString(",")

        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.accessExpireTime.toMillis())

        return AccessToken.of(
            Jwts
                .builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, userId)
                .claim(PERMISSION, authorities)
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }

    private fun createRefreshToken(userId: Long): RefreshToken {
        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.refreshExpireTime.toMillis())

        return RefreshToken.of(
            Jwts
                .builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, userId)
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }

    fun createRefreshTokenWithMember(member: Member): RefreshToken {
        val now = Date()
        val expireDate = Date(now.time + tokenExpireProvider.refreshExpireTime.toMillis())

        return RefreshToken.of(
            Jwts
                .builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(USER_PK, member.id)
                .signWith(keyProvider.key, SignatureAlgorithm.HS256)
                .compact(),
        )
    }
}
