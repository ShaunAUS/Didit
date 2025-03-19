package snoopy.didit.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import snoopy.didit.exception.AuthorizationException
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode
import snoopy.didit.template.Response

@Component
class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AuthorizationException) {
            setErrorResponse(response, e)
        } catch (e: AccessDeniedException) {
            setErrorResponse(response, BusinessException(ErrorCode.ACCESS_DENIED))
        } catch (e: ExpiredJwtException) {
            setErrorResponse(response, BusinessException(ErrorCode.EXPIRED_TOKEN))
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        ex: BusinessException,
    ) {
        logger.error(ex.errorCode.message)

        when (ex) {
            is AuthorizationException ->
                response.apply {
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    status = HttpServletResponse.SC_UNAUTHORIZED
                    writer.write(
                        ObjectMapper().writeValueAsString(
                            Response.error<Nothing>(ex.errorCode),
                        ),
                    )
                }

            else ->
                response.apply {
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    status = HttpServletResponse.SC_FORBIDDEN
                    writer.write(
                        ObjectMapper().writeValueAsString(
                            Response.error<Nothing>(ex.errorCode),
                        ),
                    )
                }
        }
    }
}
