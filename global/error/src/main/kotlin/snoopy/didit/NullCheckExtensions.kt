import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

fun <T> T?.getOrThrow(): T = this ?: throw BusinessException(ErrorCode.DTO_NULL_FIELD_EXCEPTION)
