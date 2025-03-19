package snoopy.didit.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import snoopy.didit.exception.RateLimitException
import snoopy.didit.ratelimit.RateLimitService
import snoopy.didit.security.webmvc.interceptor.config.RateLimitPathProvider

private const val HOST = "Host"

class RateLimitFilter(
    private val rateLimitService: RateLimitService,
    private val rateLimitPathProvider: RateLimitPathProvider,
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val requestUri = request.requestURI

        if (shouldApplyRateLimit(requestUri)) {
            val host = request.getHeader(HOST)

            try {
                rateLimitService.consumeToken(host)
            } catch (e: RateLimitException) {
                // GlobalException에서 처리하기 위해
                resolver.resolveException(request, response, null, e)
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun shouldApplyRateLimit(requestUri: String): Boolean {
        return rateLimitPathProvider.publicPaths.any { path ->
            requestUri.startsWith(path.removeSuffix("/**"))
        }
    }
}
