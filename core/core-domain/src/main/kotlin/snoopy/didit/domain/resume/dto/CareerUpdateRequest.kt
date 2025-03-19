package snoopy.didit.domain.resume.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class CareerUpdateRequest(
    @field:NotBlank
    val companyName: String,
    @field:NotBlank
    val departmentName: String,
    @field:NotBlank
    val positionName: String,
    val workStartDate: LocalDateTime,
    val workFinishDate: LocalDateTime? = null,
    val isWorking: Boolean,
    val memberId: Long,
    val resumeId: Long,
    val careerId: Long,
)
