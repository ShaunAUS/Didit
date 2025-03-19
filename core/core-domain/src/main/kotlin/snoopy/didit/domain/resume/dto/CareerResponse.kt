package snoopy.didit.domain.resume.dto

import snoopy.didit.domain.resume.Career
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode
import java.time.LocalDateTime

data class CareerResponse(
    val id: Long,
    val company: String?,
    val department: String?,
    val position: String?,
    val workStartDate: LocalDateTime?,
    val workFinishDate: LocalDateTime?,
    val isWorking: Boolean?,
    val memberId: Long,
    val resumeId: Long,
)

fun Career.toResponse() =
    CareerResponse(
        id = this.id ?: throw BusinessException(ErrorCode.PK_ERROR),
        company = this.company,
        department = this.department,
        position = this.position,
        workStartDate = this.workStartDate,
        workFinishDate = this.workFinishDate,
        isWorking = this.isWorking,
        memberId = this.memberId,
        resumeId = this.resumeId,
    )
