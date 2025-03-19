package snoopy.didit.api.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

private const val UNKNOWN = "unknown"
private const val REQUEST_ID = "request_id"
private const val REQUEST_IP = "request_ip"
private const val REQUEST_URL = "request_url"

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcFilter : OncePerRequestFilter() {
    companion object {
        private val IP_HEADER_CANDIDATES =
            arrayOf(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
            )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val uuid = UUID.randomUUID()

        try {
            MDC.put(REQUEST_ID, uuid.toString())
            MDC.put(REQUEST_IP, getClientIp(request))
            MDC.put(REQUEST_URL, getRequestUrl(request))

            filterChain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }

    private fun getClientIp(request: HttpServletRequest): String {
        for (header in IP_HEADER_CANDIDATES) {
            val ip = request.getHeader(header)
            if (ipValidCheck(ip)) return ip
        }

        return request.remoteAddr
    }

    private fun ipValidCheck(ip: String?): Boolean {
        return thisIsNotNullOrEmpty(ip) && thisIsNotUnknownIp(ip)
    }

    private fun thisIsNotNullOrEmpty(ip: String?) = !ip.isNullOrEmpty()

    private fun thisIsNotUnknownIp(ip: String?) = !UNKNOWN.equals(ip, ignoreCase = true)

    private fun getRequestUrl(request: HttpServletRequest): String {
        return "${request.method} ${request.requestURI}${request.queryString?.let { "?$it" } ?: ""}"
    }
}
