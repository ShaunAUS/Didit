package snoopy.didit.member

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class EmailVerifierRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    companion object {
        private const val EMAIL_VERIFICATION_PREFIX = "EmailVerification"
    }

    fun saveVerificationCode(
        email: String,
        verificationCode: String,
    ) {
        val key = "$EMAIL_VERIFICATION_PREFIX:$email"
        redisTemplate.apply {
            opsForValue().set(key, verificationCode)
            expire(key, Duration.ofMinutes(5))
        }
    }

    fun verifyCode(
        email: String,
        code: String,
    ): Boolean {
        val key = "$EMAIL_VERIFICATION_PREFIX:$email"
        val savedCode = redisTemplate.opsForValue().get(key)
        val isValid = savedCode == code
        if (isValid) redisTemplate.delete(key)

        return isValid
    }
}
