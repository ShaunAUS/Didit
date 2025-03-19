package snoopy.didit.ratelimit.config

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

private const val REFILL_TOKEN_AMOUNT = 1L
private const val REFILL_TIME = 5L
private const val BUCKET_CAPACITY = 20L
private const val DURATION_SECOND = 60L

@Configuration
class RateLimitConfig(
    private val redisClient: RedisClient,
) {
    @Bean
    fun lettuceBasedProxyManager(): LettuceBasedProxyManager<String> {
        val connect = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE))
        return LettuceBasedProxyManager.builderFor(connect)
            .withExpirationStrategy(
                ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(
                    Duration.ofSeconds(DURATION_SECOND),
                ),
            )
            .build()
    }

    @Bean
    fun bucketConfiguration(): BucketConfiguration {
        return BucketConfiguration.builder()
            .addLimit(
                Bandwidth.builder()
                    .capacity(BUCKET_CAPACITY)
                    .refillIntervally(REFILL_TOKEN_AMOUNT, Duration.ofSeconds(REFILL_TIME))
                    .build(),
            )
            .build()
    }
}
