package snoopy.didit

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import snoopy.didit.exception.BusinessException
import snoopy.didit.exception.RateLimitException
import snoopy.didit.template.ErrorCode
import snoopy.didit.template.Response

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ApiControllerAdvice {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<Response<Any>> {
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(Response.error(e.errorCode, e.data))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Response<Any>> {
        logger.error(e) { "Validation Exception" }

        val errorMessage =
            e.bindingResult.fieldErrors
                .firstOrNull()?.defaultMessage
                ?: ErrorCode.INVALID_INPUT_VALUE.message

        return ResponseEntity.ok(
            Response.error(
                ErrorCode.INVALID_INPUT_VALUE,
                errorMessage,
            ),
        )
    }

    @ExceptionHandler(RateLimitException::class)
    fun handleRateLimitExceededException(e: RateLimitException): ResponseEntity<Response<String>> {
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(Response.error(e.errorCode, e.data))
    }
}
