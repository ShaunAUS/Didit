package snoopy.didit.resume.dto

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import snoopy.didit.domain.resume.MainTask
import snoopy.didit.domain.resume.dto.toDomain
import snoopy.didit.resume.MainTaskTestFixture

class MainTaskDtoExtensionsKtTest {
    @Test
    fun `MainTaskServiceDto를 domain 객체로 변환한다`() {
        val mainTaskDto = MainTaskTestFixture.getMainTaskDto()
        val mainTask = mainTaskDto.toDomain()

        val expectedDomain =
            MainTask(
                achievement = "test 성과",
                explanation = "test explain",
                careerId = 1L,
            )

        Assertions.assertThat(mainTask).isEqualTo(expectedDomain)
    }
}
