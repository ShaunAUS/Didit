package snoopy.didit.security.extensions

import io.jsonwebtoken.Claims
import org.springframework.security.core.authority.SimpleGrantedAuthority
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

fun Claims.getAuthorities(): List<SimpleGrantedAuthority> {
    val authorities = this["role"] as? String
    return authorities
        ?.split(",")
        ?.map { SimpleGrantedAuthority("ROLE_$it") }
        ?: throw BusinessException(ErrorCode.INVALID_PERMISSION)
}

fun Claims.getUserId(): Long {
    val userId = (this["up"] as? Number)?.toLong()
    return userId ?: throw BusinessException(ErrorCode.FAIL_EXTRACTION_TOKEN)
}
