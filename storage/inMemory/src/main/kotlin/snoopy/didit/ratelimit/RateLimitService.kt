package snoopy.didit.ratelimit

import io.github.bucket4j.Bucket
import org.springframework.stereotype.Component
import snoopy.didit.exception.RateLimitException
import snoopy.didit.ratelimit.config.RateLimitConfig
import snoopy.didit.template.ErrorCode

private const val CONSUME_TOKEN_AMOUNT = 1L

@Component
class RateLimitService(
    private val rateLimitConfig: RateLimitConfig,
) {
    private val proxyManager = rateLimitConfig.lettuceBasedProxyManager()

    fun consumeToken(apiKey: String): Boolean {
        return consumeBucketToken(getOrCreateBucket(apiKey))
    }

    /**
     * Bucket을 Redis에서 관리하도록 한다.
     */
    private fun getOrCreateBucket(apiKey: String): Bucket {
        return proxyManager
            .builder()
            .build(apiKey) { rateLimitConfig.bucketConfiguration() }
    }

    private fun consumeBucketToken(bucket: Bucket): Boolean {
        if (notEnoughBucketToken(bucket)) {
            throw RateLimitException(ErrorCode.RATE_LIMIT)
        }
        return true
    }

    private fun notEnoughBucketToken(bucket: Bucket) = !bucket.tryConsume(CONSUME_TOKEN_AMOUNT)
}
