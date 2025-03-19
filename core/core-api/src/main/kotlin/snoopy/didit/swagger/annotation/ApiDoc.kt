package snoopy.didit.swagger.annotation

import io.swagger.v3.oas.annotations.Operation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation
annotation class ApiDoc(
    val summary: String = "",
    val description: String = "",
    val success: String = "",
    val error: String = "",
)
