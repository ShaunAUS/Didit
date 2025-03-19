package snoopy.didit.domain.member.dto

data class JobFamilyDto(
    val jobFamilyNumber: Int,
    val jobFamilyName: String,
    val jobRoles: List<JobRoleDto>,
)
