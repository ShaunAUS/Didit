package snoopy.didit.auth

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class AuthRepository(
    private val jwtConfig: JwtConfig,
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    companion object {
        private const val REFRESH_PREFIX = "refresh"
        private const val ACCESS_PREFIX = "access"
    }

    fun deleteTokens(userId: Long): Boolean =
        try {
            deleteToken(userId)
            true
        } catch (e: Exception) {
            throw RuntimeException("Don't delete token")
        }

    fun addTokens(
        refreshToken: String,
        accessToken: String,
        userId: Long,
    ) {
        saveToken(REFRESH_PREFIX, refreshToken, userId, jwtConfig.refreshExpireTime)
        saveToken(ACCESS_PREFIX, accessToken, userId, jwtConfig.accessExpireTime)
    }

    fun findAccessToken(userId: Long): Boolean {
        val result =
            redisTemplate
                .opsForValue()
                .get("$ACCESS_PREFIX:$userId")

        return result?.let { true } ?: false
    }

    fun findRefreshToken(userId: Long): Boolean {
        val result =
            redisTemplate
                .opsForValue()
                .get("$REFRESH_PREFIX:$userId")

        return result?.let { true } ?: false
    }

    private fun saveToken(
        prefix: String,
        token: String,
        userId: Long,
        expireTime: Long,
    ) {
        redisTemplate
            .opsForValue()
            .set("$prefix:$userId", token, Duration.ofSeconds(expireTime))
    }

    private fun deleteToken(userId: Long) {
        redisTemplate.delete("$ACCESS_PREFIX:$userId")
        redisTemplate.delete("$REFRESH_PREFIX:$userId")
    }
}
