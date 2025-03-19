package snoopy.didit.fixture

import snoopy.didit.domain.resume.dto.ResumeCreateRequest

class ResumeRequestFixture {
    companion object {
        fun createRequest() =
            ResumeCreateRequest(
                memberId = 1L,
            )
    }
}
