package snoopy.didit.resume

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import snoopy.didit.domain.resume.Career

class CareerTest {
    @Test
    fun `Career2는 초기 생성될 때 memberId, resumeId는 null이면 안된다`() {
        val career1 =
            Career(
                memberId = 1L,
                resumeId = 2L,
            ).apply { updateId(1L) }
        val career =
            Career(
                memberId = 1L,
                resumeId = 2L,
            ).apply { updateId(1L) }

        Assertions.assertThat(career1).isEqualTo(career)
    }
}
