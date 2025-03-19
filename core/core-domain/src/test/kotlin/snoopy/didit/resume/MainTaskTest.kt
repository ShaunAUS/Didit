package snoopy.didit.resume

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import snoopy.didit.domain.resume.MainTask

class MainTaskTest {
    @Test
    fun `성과 객체는 성과, 설명, 커리어 Id 값이 필수이다`() {
        Assertions.assertThatThrownBy {
            MainTask(
                achievement = "",
                explanation = "test explain",
                careerId = 1L,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)

        Assertions.assertThatThrownBy {
            MainTask(
                achievement = "test 주요 성과",
                explanation = "",
                careerId = 1L,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)

        Assertions.assertThatThrownBy {
            MainTask(
                achievement = "test 주요 성과",
                explanation = "test explain",
                careerId = 0,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `같은 내용의 MainTask를 비교하면 같다고 한다`() {
        val mainTask1 =
            MainTask(
                achievement = "test 주요 성과",
                explanation = "test explain",
                careerId = 1L,
            )
        val mainTask2 =
            MainTask(
                achievement = "test 주요 성과",
                explanation = "test explain",
                careerId = 1L,
            )

        Assertions.assertThat(mainTask1).isEqualTo(mainTask2)
    }
}
