package snoopy.didit.resume

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import snoopy.didit.domain.resume.MainTaskAppender
import snoopy.didit.domain.resume.MainTaskRepository
import snoopy.didit.domain.resume.dto.toDomain

@ActiveProfiles("test")
@SpringBootTest(classes = [MainTaskAppender::class])
class MainTaskAppenderTest {
    private lateinit var sut: MainTaskAppender

    @MockkBean
    private lateinit var mainTaskRepository: MainTaskRepository

    @BeforeEach
    fun setUp() {
        sut = MainTaskAppender(mainTaskRepository)
    }

    @Test
    fun `MainTask를 저장한다`() {
        val mainTaskRequest = MainTaskTestFixture.getMainTaskDto()

        val mainTask = mainTaskRequest.toDomain()
        every { mainTaskRepository.save(mainTask) } returns Unit

        sut.save(mainTaskRequest)
    }
}
