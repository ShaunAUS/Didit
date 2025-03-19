package snoopy.didit.auth

import io.lettuce.core.RedisClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

private const val INDEX = 1

@Configuration
class RedisConfig {
    @Value("\${spring.redis.host}")
    private lateinit var host: String

    @Value("\${spring.redis.port}")
    private var port: Int = 0

    @Value("\${spring.redis.password}")
    private lateinit var password: String

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration()
        redisConfig.hostName = host
        redisConfig.port = port
        redisConfig.password = RedisPassword.of(password)

        return LettuceConnectionFactory(redisConfig)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> =
        RedisTemplate<String, Any>().apply {
            this.connectionFactory = redisConnectionFactory()
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = StringRedisSerializer()
        }

    @Bean
    fun rateLimitRedisClient(): RedisClient {
        // 기존 설정값을 재사용하면서 DB 인덱스만 다르게 설정
        return RedisClient.create("redis://$password@$host:$port/$INDEX")
    }
}
