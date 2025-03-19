package snoopy.didit.resume

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import snoopy.didit.resume.ResumeTestFixture.Companion.createResume

class ResumeTest {
    @Test
    fun `같은 값을 가진 Resume는 서로 같은 객체이다`() {
        val resume1 =
            createResume(
                phoneNumber = "01012341234",
                email = "test@example.com",
                introduce = "test introduce",
            )
        val resume2 =
            createResume(
                phoneNumber = "01012341234",
                email = "test@example.com",
                introduce = "test introduce",
            )

        assertThat(resume1).isEqualTo(resume2)
    }
}
