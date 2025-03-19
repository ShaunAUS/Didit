package snoopy.didit.exception

import snoopy.didit.template.ErrorCode

class RateLimitException(
    val errorCode: ErrorCode,
    val data: Any? = null,
) : RuntimeException(errorCode.message)
