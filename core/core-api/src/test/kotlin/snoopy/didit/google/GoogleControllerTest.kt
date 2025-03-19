package snoopy.didit.google

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.api.google.GoogleController
import snoopy.didit.api.google.GoogleService
import snoopy.didit.fixture.GoogleControllerTestFixture
import snoopy.util.ControllerTestSupport

private const val GOOGLE_LOGIN_API = "/api/v1/google/login"

@WebMvcTest(controllers = [GoogleController::class])
class GoogleControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var googleService: GoogleService

    @Test
    @WithMockUser
    fun `구글 로그인을 한다`() {
        val response = GoogleControllerTestFixture.getGoogleLoginResponse()

        every { googleService.googleLogin(any()) } returns (response)

        mockMvc.perform(
            get(GOOGLE_LOGIN_API)
                .param("code", "code"),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").value("accessToken"))
            .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
    }
}
