package snoopy.didit.resume

import snoopy.didit.domain.resume.Resume

class ResumeTestFixture {
    companion object {
        fun createResume(
            phoneNumber: String,
            email: String,
            introduce: String,
        ) = Resume(
            phoneNumber = phoneNumber,
            email = email,
            oneLineIntroduction = introduce,
            memberId = 1L,
        )
    }
}
