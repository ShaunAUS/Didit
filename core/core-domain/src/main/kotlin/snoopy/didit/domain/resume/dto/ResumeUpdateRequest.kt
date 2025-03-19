package snoopy.didit.domain.resume.dto

import jakarta.validation.constraints.NotBlank

data class ResumeUpdateRequest(
    @field:NotBlank
    val phoneNumber: String,
    val email: String,
    val introduction: String,
    val memberId: Long,
    val resumeId: Long,
)
