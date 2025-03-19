package snoopy.didit.fixture

import snoopy.didit.domain.resume.dto.MainTaskRequest

class MainTaskRequestFixture {
    companion object {
        fun getRequestWithoutDates() =
            MainTaskRequest(
                achievement = "test",
                explain = "test explain",
                careerId = 1L,
            )

        fun getRequestBlankAchievement() =
            MainTaskRequest(
                achievement = "",
                explain = "test explain",
                careerId = 1L,
            )

        fun getRequestBlankExplain() =
            MainTaskRequest(
                achievement = "test",
                explain = "",
                careerId = 1L,
            )

        fun getRequestMinusCareerId() =
            MainTaskRequest(
                achievement = "test",
                explain = "test explain",
                careerId = -1,
            )
    }
}
