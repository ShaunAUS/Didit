package snoopy.didit.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {
    var accessExpireTime: Long = 0
    var refreshExpireTime: Long = 0
}
