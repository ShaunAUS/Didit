package snoopy.didit.domain.resume.dto

import jakarta.validation.constraints.Positive

data class ResumeCreateRequest(
    @field:Positive
    val memberId: Long,
)
