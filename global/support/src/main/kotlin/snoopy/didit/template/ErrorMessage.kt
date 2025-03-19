package snoopy.didit.template

data class ErrorMessage private constructor(
    val code: String,
    val message: String,
    val data: Any? = null,
) {
    constructor(errorCode: ErrorCode, data: Any? = null) : this(
        code = errorCode.errorCode,
        message = errorCode.message,
        data = data,
    )
}
