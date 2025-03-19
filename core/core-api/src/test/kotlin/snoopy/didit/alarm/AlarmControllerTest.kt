package snoopy.didit.alarm

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.api.alarm.AlarmController
import snoopy.didit.domain.alarm.AlarmFacade
import snoopy.didit.fixture.AlarmControllerTestFixture
import snoopy.util.ControllerTestSupport

private const val CREATE_ALARM = "/api/v1/alarm"
private const val UPDATE_ALARM = "/api/v1/alarm"
private const val UPDATE_ALARM_ENABLE = "/api/v1/alarm/enable"
private const val MEMBER_ID_ANNOTATION = "memberId"
private const val IS_ALARM_ENABLE = "is_alarm_enable"

@WebMvcTest(controllers = [AlarmController::class])
class AlarmControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var alarmFacade: AlarmFacade

    @Test
    @WithMockUser
    fun `알람 생성시 시간이 null이면 V001 에러를 뱉는다`() {
        val request = AlarmControllerTestFixture.getNullTimeCreateAlarmRequest()

        mockMvc.perform(
            post(CREATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("알람 시간은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `알람 생성시 요일이 null이면 V001 에러를 뱉는다`() {
        val request = AlarmControllerTestFixture.getNullWeekDayCreateAlarmRequest()

        mockMvc.perform(
            post(CREATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("알림요일 선택은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `알람 생성시 오전 오후 선택이 null이면 V001 에러를 뱉는다`() {
        val request = AlarmControllerTestFixture.getNullTimeOfDayCreateAlarmRequest()

        mockMvc.perform(
            post(CREATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("오전/오후 선택은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `알람 생성시 이메일 알람 활성화 여부가 Null이면 V001 에러를 뱉는다`() {
        val request = AlarmControllerTestFixture.getNullIsAlarmEnableCreateAlarmRequest()

        mockMvc.perform(
            post(CREATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("이메일 알람 활성화 여부는 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `알람을 생성한다`() {
        val request = AlarmControllerTestFixture.getCreateAlarmRequest()
        val response = AlarmControllerTestFixture.getAlarmInfoResponse()

        every { alarmFacade.createAlarm(any(), any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.time").value("12:12:00"))
            .andExpect(jsonPath("$.data.day[0]").value("MONDAY"))
            .andExpect(jsonPath("$.data.day[1]").value("TUESDAY"))
            .andExpect(jsonPath("$.data.isAlarmEnable").value(true))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `로그인한 유져의 알람 정보를 가져온다`() {
        val response = AlarmControllerTestFixture.getAlarmInfoResponse()

        every { alarmFacade.findMyAlarmInfo(any()) }.returns(response)

        mockMvc.perform(
            get("/api/v1/alarm")
                .param(MEMBER_ID_ANNOTATION, "1"),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.time").value("12:12:00"))
            .andExpect(jsonPath("$.data.day[0]").value("MONDAY"))
            .andExpect(jsonPath("$.data.day[1]").value("TUESDAY"))
            .andExpect(jsonPath("$.data.isAlarmEnable").value(true))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `이메일 알람을 true로 수정한다`() {
        every { alarmFacade.updateEmailAlarmEnable(any(), any()) }.returns(Unit)

        mockMvc.perform(
            patch(UPDATE_ALARM_ENABLE)
                .param(MEMBER_ID_ANNOTATION, "1")
                .param(IS_ALARM_ENABLE, "true")
                .with(csrf()),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `알람을 수정한다`() {
        val request = AlarmControllerTestFixture.getUpdateAlarmRequest()
        val response = AlarmControllerTestFixture.getUpdatedAlarmInfoResponse()

        every { alarmFacade.updateAlarm(any(), any()) }.returns(response)

        mockMvc.perform(
            patch(UPDATE_ALARM)
                .content(objectMapper.writeValueAsString(request))
                .param(MEMBER_ID_ANNOTATION, "1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.time").value("09:30:00"))
            .andExpect(jsonPath("$.data.day[0]").value("WEDNESDAY"))
            .andExpect(jsonPath("$.data.day[1]").value("THURSDAY"))
            .andExpect(jsonPath("$.data.isAlarmEnable").value(true))
    }
}
