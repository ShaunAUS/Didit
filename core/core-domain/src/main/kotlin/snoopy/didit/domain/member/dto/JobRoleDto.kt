package snoopy.didit.domain.member.dto

import snoopy.didit.member.JobRole

data class JobRoleDto(
    val jobRoleNumber: Int,
    val jobRoleName: String,
)

fun JobRole.toDto(): JobRoleDto =
    JobRoleDto(
        jobRoleNumber = this.number,
        jobRoleName = this.positionName,
    )
