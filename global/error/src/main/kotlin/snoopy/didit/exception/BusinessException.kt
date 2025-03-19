package snoopy.didit.exception

import snoopy.didit.template.ErrorCode

open class BusinessException(
    val errorCode: ErrorCode,
    val data: Any? = null,
) : RuntimeException(errorCode.message)
