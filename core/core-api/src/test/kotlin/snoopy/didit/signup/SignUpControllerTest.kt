package snoopy.didit.signup

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.domain.member.EmailVerifier
import snoopy.didit.domain.member.SignUpService
import snoopy.didit.fixture.SignUpControllerTestFixture
import snoopy.didit.member.SignUpController
import snoopy.util.ControllerTestSupport

private const val SIGNUP_API = "/api/v1/signup"
private const val EMAIL_VERIFICATION_API = "/api/v1/email-verification"
private const val EMAIL_PARAMETER = "email"
private const val EMAIL_PARAMETER_EXAMPLE = "didit@gmail.com"
private const val CODE_PARAMETER = "code"
private const val CODE_PARAMETER_EXAMPLE = "123456"

@WebMvcTest(controllers = [SignUpController::class])
class SignUpControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var signUpService: SignUpService

    @MockkBean
    lateinit var emailVerifier: EmailVerifier

    @Test
    @WithMockUser
    fun `회원가입을 한다`() {
        val request = SignUpControllerTestFixture.getSignUpDto()

        every { signUpService.signUp(any()) }.returns(Unit)
        mockMvc.perform(
            post(SIGNUP_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `이메일 인증을 한다`() {
        every { emailVerifier.startEmailVerification(any()) } returns Unit
        mockMvc.perform(
            post(EMAIL_VERIFICATION_API)
                .param(EMAIL_PARAMETER, EMAIL_PARAMETER_EXAMPLE)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `인증 인증 코드를 확인한다`() {
        every { emailVerifier.verifyCode(any(), any()) } returns Unit
        mockMvc.perform(
            get(EMAIL_VERIFICATION_API)
                .param(EMAIL_PARAMETER, EMAIL_PARAMETER_EXAMPLE)
                .param(CODE_PARAMETER, CODE_PARAMETER_EXAMPLE)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `회원 가입시 이메일이 입력되지 않으면 V001에러를 뱉는다`() {
        val request = SignUpControllerTestFixture.getNullEmailSignUpDto()

        mockMvc.perform(
            post(SIGNUP_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("이메일은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `회원가입시 비밀번호가 입력되지 않으면 V001에러를 뱉는다`() {
        val request = SignUpControllerTestFixture.getNullPassWordSignUpDto()

        mockMvc.perform(
            post(SIGNUP_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("비밀번호는 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `회원가입시 이름이 입력되지 않으면 V001에러를 뱉는다`() {
        val request = SignUpControllerTestFixture.getNullNameSignUpDto()

        mockMvc.perform(
            post(SIGNUP_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("이름은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `회원가입시 닉네임이 입력되지 않으면 V001에러를 뱉는다`() {
        val request = SignUpControllerTestFixture.getNullNickNameSignUpDto()

        mockMvc.perform(
            post(SIGNUP_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("ERROR"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.code").value("V001"))
            .andExpect(jsonPath("$.error.message").value("Invalid Input Value"))
            .andExpect(jsonPath("$.error.data").value("닉네임은 필수 입력값입니다"))
    }
}
