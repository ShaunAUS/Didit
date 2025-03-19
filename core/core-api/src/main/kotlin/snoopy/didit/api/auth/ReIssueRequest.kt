package snoopy.didit.api.auth

import jakarta.validation.constraints.NotBlank

data class ReIssueRequest(
    val userId: Long,
    @field:NotBlank
    val refreshToken: String,
)
