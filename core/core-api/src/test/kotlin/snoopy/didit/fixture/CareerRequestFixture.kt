package snoopy.didit.fixture

import snoopy.didit.domain.resume.dto.CareerCreateDto
import snoopy.didit.domain.resume.dto.CareerUpdateRequest
import java.time.LocalDateTime

class CareerRequestFixture {
    companion object {
        fun getMinusMemberId() =
            CareerCreateDto(
                memberId = 0,
                resumeId = 1L,
            )

        fun getMinusResumeId() =
            CareerCreateDto(
                memberId = 1L,
                resumeId = 0,
            )

        fun getCareerRequest() =
            CareerCreateDto(
                memberId = 1L,
                resumeId = 2L,
            )

        fun getUpdateRequest() =
            CareerUpdateRequest(
                companyName = "네이버",
                departmentName = "플랫폼개발팀",
                positionName = "백엔드 개발자",
                workStartDate = LocalDateTime.parse("2024-01-24T14:30:00"),
                workFinishDate = LocalDateTime.parse("2025-12-31T23:59:59"),
                isWorking = true,
                memberId = 1L,
                resumeId = 1L,
                careerId = 1L,
            )
    }
}
