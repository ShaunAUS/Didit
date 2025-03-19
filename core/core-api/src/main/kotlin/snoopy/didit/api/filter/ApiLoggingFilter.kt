package snoopy.didit.api.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingResponseWrapper

private val log = KotlinLogging.logger {}

/**
 * API 요청과 응답을 로깅하는 필터
 */
@Component
class ApiLoggingFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        when (request) {
            is HttpServletRequest -> {
                if (actuatorRequest(request, chain, response)) return

                // 요청과 응답을 래핑
                val wrappedRequest = CachedBodyHttpServletRequest(request)
                val wrappedResponse = ContentCachingResponseWrapper(response as HttpServletResponse)

                try {
                    // 요청 정보 로깅
                    val url = wrappedRequest.requestURI
                    val method = wrappedRequest.method
                    val requestBody = wrappedRequest.reader.lines().reduce("") { acc, line -> acc + line }

                    log.info { "Incoming Request: URL=$url, Method=$method, Body=$requestBody" }

                    // 필터 체인 실행
                    chain.doFilter(wrappedRequest, wrappedResponse)

                    // 응답 정보 로깅
                    val responseBody = String(wrappedResponse.contentAsByteArray, Charsets.UTF_8)
                    val status = wrappedResponse.status

                    log.info { "Outgoing Response: URL=$url, Method=$method, Status=$status, Body=$responseBody" }
                } finally {
                    // 응답 데이터를 클라이언트에게 전송
                    wrappedResponse.copyBodyToResponse()
                }
            }
            else -> chain.doFilter(request, response)
        }
    }

    private fun actuatorRequest(
        request: HttpServletRequest,
        chain: FilterChain,
        response: ServletResponse,
    ): Boolean {
        if (request.requestURI.startsWith("/actuator")) {
            chain.doFilter(request, response)
            return true
        }
        return false
    }
}
