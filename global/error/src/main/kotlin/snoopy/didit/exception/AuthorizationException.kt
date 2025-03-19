package snoopy.didit.exception

import snoopy.didit.template.ErrorCode

class AuthorizationException(
    errorCode: ErrorCode,
    data: Any? = null,
) : BusinessException(errorCode, data)
