package snoopy.didit.resume

import snoopy.didit.domain.resume.Career

class CareerTestFixture {
    companion object {
        fun getCareerWithId(): Career {
            val career =
                Career(
                    memberId = 1L,
                    resumeId = 2L,
                )
            career.updateId(1L)
            return career
        }
    }
}
