package snoopy.didit.security.extensions

import org.springframework.security.core.Authentication
import snoopy.didit.api.auth.CustomUserDetails
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

fun Authentication.getUserId(): Long {
    val principal =
        this.principal as? CustomUserDetails
            ?: throw BusinessException(ErrorCode.FAIL_EXTRACTION_TOKEN)
    return principal.getId()
}
