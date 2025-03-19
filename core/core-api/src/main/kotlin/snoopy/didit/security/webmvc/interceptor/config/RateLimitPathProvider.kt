package snoopy.didit.security.webmvc.interceptor.config

import org.springframework.stereotype.Component

@Component
class RateLimitPathProvider {
    val publicPaths =
        arrayOf(
            RateLimitPathConstants.ALL,
        )
}
