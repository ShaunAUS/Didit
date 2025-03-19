package snoopy.didit.resume

import snoopy.didit.domain.resume.dto.MainTaskRequest

class MainTaskTestFixture {
    companion object {
        fun getMainTaskDto() =
            MainTaskRequest(
                achievement = "test 성과",
                explain = "test explain",
                careerId = 1L,
            )
    }
}
