package snoopy.didit.sse

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.api.sse.NotificationController
import snoopy.didit.domain.sse.NotificationEmitterFacade
import snoopy.util.ControllerTestSupport

// TODO sse testcode 이렇게 짜는게 맞는지 추후 확인필요
@WebMvcTest(controllers = [NotificationController::class])
class NotificationControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var notificationEmitterFacade: NotificationEmitterFacade

    @Test
    @WithMockUser
    fun `구독을 한다`() {
        val lastEventId = "lastEventId"
        val sseEmitter = SseEmitter()

        every { notificationEmitterFacade.subscribe(any(), any()) } returns sseEmitter

        mockMvc.perform(
            get("/api/v1/notification/subscribe")
                .header("last-Event-ID", lastEventId)
                .with(csrf())
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE),
        )
            .andDo(print())
            .andExpect(status().isOk())
    }

    @Test
    @WithMockUser
    fun `알림을 읽는다`() {
        val emitterId = "1_123456"
        val notificationId = 1L

        every { notificationEmitterFacade.readNotification(any(), any()) } returns Unit

        mockMvc.perform(
            patch("/api/v1/notification/read/$notificationId")
                .param("emitter_id", emitterId)
                .with(csrf()),
        )
            .andDo(print())
            .andExpect(status().isOk())
    }
}
