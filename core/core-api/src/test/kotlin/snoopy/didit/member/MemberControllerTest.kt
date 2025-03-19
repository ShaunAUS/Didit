package snoopy.didit.member

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.api.member.MemberController
import snoopy.didit.domain.member.JobService
import snoopy.didit.domain.member.MemberService
import snoopy.didit.domain.memoritag.MemoirTagFacade
import snoopy.didit.fixture.MemoirControllerTestFixture
import snoopy.didit.memoir.Calendar
import snoopy.util.ControllerTestSupport

private const val GET_MEMOIR_API = "/api/v1/members/{member_id}/space/memoirs"
private const val MEMBER_ID = 1
private const val GET_MEMOIR_BY_DATE_API = "/api/v1/members/{member_id}/date/memoirs"
private const val CALCULATE_CONTINOUS_MEMOIR_API = "/api/v1/members/{member_id}/calendar/memoirs"
private const val YEAR_PARAM = "year"
private const val MONTH_PARAM = "month"
private const val YEAR_PARAM_VALUE = "2021"
private const val MONTH_PARAM_VALUE = "1"

@WebMvcTest(controllers = [MemberController::class])
class MemberControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var memberService: MemberService

    @MockkBean
    lateinit var memoirTagFacade: MemoirTagFacade

    @MockkBean
    lateinit var jobService: JobService

    @Test
    @WithMockUser
    fun `해당_유저가_작성한_회고록을_스페이스별로_그룹핑하여_조회한다`() {
        val response = MemoirControllerTestFixture.getMemoirInfoDtos()
        every { memoirTagFacade.findMemoirTagsBy(any()) }.returns(response)
        mockMvc.perform(
            get(GET_MEMOIR_API, MEMBER_ID),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data[0].spaceName").value("공간1"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].title").value("회고록 제목"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].content").value("회고록 내용"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].tags[0].name").value("태그1"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].tags[1].name").value("태그2"))
            .andExpect(jsonPath("$.data[1].spaceName").value("공간2"))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].id").value(2))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].title").value("회고록 제목2"))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].content").value("회고록 내용2"))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].tags[0].name").value("태그3"))
            .andExpect(jsonPath("$.data[1].memoirTagResponses[0].tags[1].name").value("태그4"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `해당_유저가_쓴_회고록을_날짜로_조회한다`() {
        val response = MemoirControllerTestFixture.getMemoirInfoWithDateDtos()

        every { memoirTagFacade.findMemoirTagsByDate(any(), any(), any()) }.returns(listOf(response))

        mockMvc.perform(
            get(GET_MEMOIR_BY_DATE_API, MEMBER_ID)
                .param(YEAR_PARAM, YEAR_PARAM_VALUE)
                .param(MONTH_PARAM, Calendar.JANUARY.toString()),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data[0].month").value(1))
            .andExpect(jsonPath("$.data[0].weekNumber").value(1))
            .andExpect(jsonPath("$.data[0].startDay").value(5))
            .andExpect(jsonPath("$.data[0].endDay").value(15))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].id").value(1))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].writeDate").value("2021-01-07T00:00:00"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].title").value("회고록 제목"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].content").value("회고록 내용"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].tags[0].name").value("태그1"))
            .andExpect(jsonPath("$.data[0].memoirTagResponses[0].tags[1].name").value("태그2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `회고록의_연속_일수를_계산한다`() {
        val response = MemoirControllerTestFixture.getMemoirContinuousWritingDto()
        every { memoirTagFacade.calculateContinuousWritingMemoirTag(any(), any()) }.returns(response)
        mockMvc.perform(
            get(CALCULATE_CONTINOUS_MEMOIR_API, MEMBER_ID)
                .param(YEAR_PARAM, YEAR_PARAM_VALUE)
                .param(MONTH_PARAM, Calendar.JANUARY.toString()),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.continuousWriteDay").value(3))
            .andExpect(jsonPath("$.data.writtenDates[0]").value("2021-01-01"))
            .andExpect(jsonPath("$.data.writtenDates[1]").value("2021-01-02"))
            .andExpect(jsonPath("$.data.writtenDates[2]").value("2021-01-03"))
            .andExpect(jsonPath("$.data.totalMemoirCount").value(3))
            .andExpect(jsonPath("$.data.randomMessage").value("랜덤 메시지"))
            .andExpect(jsonPath("$.error").isEmpty())
    }
}
