package snoopy.didit.resume.dto

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import snoopy.didit.domain.resume.dto.CareerResponse
import snoopy.didit.domain.resume.dto.toResponse
import snoopy.didit.resume.CareerTestFixture

class CareerDtoExtensionsKtTest {
    @Test
    fun `Career를 Reponse 객체로 변환한다`() {
        val career = CareerTestFixture.getCareerWithId()
        val response = career.toResponse()

        val expectedResponse =
            CareerResponse(
                id = 1L,
                memberId = 1L,
                resumeId = 2L,
                company = null,
                department = null,
                position = null,
                workStartDate = null,
                workFinishDate = null,
                isWorking = null,
            )

        Assertions.assertThat(response).isEqualTo(expectedResponse)
    }
}
