package snoopy.didit.domain.resume.dto

import jakarta.validation.constraints.Positive

data class CareerCreateDto(
    @field:Positive
    val memberId: Long,
    @field:Positive
    val resumeId: Long,
)
