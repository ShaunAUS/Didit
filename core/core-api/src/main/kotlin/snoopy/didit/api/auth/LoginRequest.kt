package snoopy.didit.api.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String,
    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 6, message = "Password size is invalid")
    val password: String,
)
