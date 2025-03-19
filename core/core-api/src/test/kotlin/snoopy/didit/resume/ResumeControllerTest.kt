package snoopy.didit.resume

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.api.resume.ResumeController
import snoopy.didit.domain.resume.CareerAppender
import snoopy.didit.domain.resume.CareerProcessor
import snoopy.didit.domain.resume.MainTaskAppender
import snoopy.didit.domain.resume.ResumeAppender
import snoopy.didit.domain.resume.ResumeProcessor
import snoopy.didit.domain.resume.dto.CareerResponse
import snoopy.didit.domain.resume.dto.ResumeResponse
import snoopy.didit.fixture.CareerRequestFixture
import snoopy.didit.fixture.MainTaskRequestFixture
import snoopy.didit.fixture.ResumeRequestFixture
import snoopy.util.ControllerTestSupport

private const val MAIN_TASK_CREATE_API = "/api/v1/resume/main-task/new"
private const val CAREER_CREATE_API = "/api/v1/resume/career/new"
private const val RESUME_INIT_API = "/api/v1/resume/resume/new"
private const val CAREER_UPDATE_API = "/api/v1/resume/career"

@WebMvcTest(controllers = [ResumeController::class])
class ResumeControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var mainTaskAppender: MainTaskAppender

    @MockkBean
    lateinit var careerAppender: CareerAppender

    @MockkBean
    lateinit var resumeAppender: ResumeAppender

    @MockkBean
    lateinit var careerProcessor: CareerProcessor

    @MockkBean
    lateinit var resumeProcessor: ResumeProcessor

    @Test
    @WithMockUser
    fun `RequestBody에서 workStartDate와 workFinishDate는 null일 수 있다`() {
        val requestWithoutDates = MainTaskRequestFixture.getRequestWithoutDates()

        every { mainTaskAppender.save(requestWithoutDates) } returns Unit

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(MAIN_TASK_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestWithoutDates),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
    }

    @Test
    @WithMockUser
    fun `RequestBody에서 acheivement가 빈 값이면 V001이 발생한다`() {
        val requestBlankAchievement = MainTaskRequestFixture.getRequestBlankAchievement()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(MAIN_TASK_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestBlankAchievement),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
    }

    @Test
    @WithMockUser
    fun `RequestBody에서 explain이 빈 값이면 V001이 발생한다`() {
        val requestBlankExplain = MainTaskRequestFixture.getRequestBlankExplain()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(MAIN_TASK_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestBlankExplain),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
    }

    @Test
    @WithMockUser
    fun `RequestBody에서 careerId가 음수면 V001이 발생한다`() {
        val requestMinusCareerId = MainTaskRequestFixture.getRequestMinusCareerId()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(MAIN_TASK_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestMinusCareerId),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
    }

    @Test
    @WithMockUser
    fun `RequestBody에서 memberId가 음수면 V001이 발생한다`() {
        val requestMinusMemberId = CareerRequestFixture.getMinusMemberId()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(CAREER_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestMinusMemberId),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
    }

    @Test
    @WithMockUser
    fun `RequestBody에서 resumeId가 음수면 V001이 발생한다`() {
        val requestMinusResumeId = CareerRequestFixture.getMinusResumeId()

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(CAREER_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(requestMinusResumeId),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
    }

    @Test
    @WithMockUser
    fun `정상 요청이 오면 초기 Career를 생성한다`() {
        val request = CareerRequestFixture.getCareerRequest()

        val careerResponse =
            CareerResponse(
                id = 1L,
                memberId = 2L,
                resumeId = 3L,
                company = null,
                department = null,
                position = null,
                workStartDate = null,
                workFinishDate = null,
                isWorking = null,
            )

        every { careerAppender.init(request) } returns careerResponse

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(CAREER_CREATE_API)
                    .content(
                        objectMapper.writeValueAsString(request),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.memberId").value(2))
            .andExpect(jsonPath("$.data.resumeId").value(3))
            .andExpect(jsonPath("$.error").value(null))
    }

    @Test
    @WithMockUser
    fun `정상 요청이 오면 초기 Resume를 생성한다`() {
        val request = ResumeRequestFixture.createRequest()

        val resumeResponse =
            ResumeResponse(
                resumeId = 1L,
                phoneNumber = "01012341234",
                email = "test@example.com",
                introduce = "",
            )

        every { resumeAppender.init(request) } returns resumeResponse

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post(RESUME_INIT_API)
                    .content(
                        objectMapper.writeValueAsString(request),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.resumeId").value(1))
            .andExpect(jsonPath("$.data.phoneNumber").value("01012341234"))
            .andExpect(jsonPath("$.data.email").value("test@example.com"))
            .andExpect(jsonPath("$.data.introduce").value(""))
            .andExpect(jsonPath("$.error").value(null))
    }

    @Test
    @WithMockUser
    fun `커리어를 수정한다`() {
        val request = CareerRequestFixture.getUpdateRequest()

        every { careerProcessor.update(any()) } just runs

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put(CAREER_UPDATE_API)
                    .content(
                        objectMapper.writeValueAsString(request),
                    ).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON),
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").value(null))
            .andExpect(jsonPath("$.error").value(null))
    }
}
