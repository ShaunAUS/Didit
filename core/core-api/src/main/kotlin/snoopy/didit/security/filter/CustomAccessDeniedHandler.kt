package snoopy.didit.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import snoopy.didit.template.ErrorCode
import snoopy.didit.template.Response

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        response.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = HttpServletResponse.SC_FORBIDDEN
            writer.write(
                ObjectMapper().writeValueAsString(
                    Response.error<Nothing>(ErrorCode.ACCESS_DENIED),
                ),
            )
        }
    }
}
