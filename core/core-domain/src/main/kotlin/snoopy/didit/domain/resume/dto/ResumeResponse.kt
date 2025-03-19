package snoopy.didit.domain.resume.dto

import snoopy.didit.domain.resume.Resume
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

data class ResumeResponse(
    val resumeId: Long,
    val phoneNumber: String,
    val email: String,
    val introduce: String,
)

fun Resume.toResponse() =
    ResumeResponse(
        resumeId = this.id ?: throw BusinessException(ErrorCode.PK_ERROR),
        phoneNumber = this.phoneNumber ?: "",
        email = this.email,
        introduce = this.oneLineIntroduction ?: "",
    )
