package snoopy.didit.domain.member.dto

import getOrThrow
import snoopy.didit.domain.member.Member
import snoopy.didit.member.JobFamily
import snoopy.didit.member.JobRole

fun SignUpDto.toMemberEntity(encodedPassword: String) =
    Member(
        email = this.email.getOrThrow(),
        password = encodedPassword,
        name = this.name.getOrThrow(),
        nickName = this.nickName.getOrThrow(),
        jobFamily = this.jobFamily?.let { JobFamily.toInt(it) } ?: JobFamily.toInt(JobFamily.NOT_SELECTED),
        jobRole = this.jobRole?.let { JobRole.toInt(it) } ?: JobRole.toInt(JobRole.NOT_SELECTED),
    )
