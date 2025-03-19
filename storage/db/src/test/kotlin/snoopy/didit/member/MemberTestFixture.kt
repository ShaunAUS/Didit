package snoopy.didit.member

import snoopy.didit.domain.member.Member

class MemberTestFixture {
    companion object {
        fun getMember(): Member {
            return Member(
                email = "didit@gmail.com",
                password = "test12341234",
                name = "석규나",
                nickName = "사기꾼",
                jobFamily = JobFamily.toInt(JobFamily.DEVELOPMENT),
                jobRole = JobRole.toInt(JobRole.BACKEND_DEVELOPER),
            )
        }
    }
}
