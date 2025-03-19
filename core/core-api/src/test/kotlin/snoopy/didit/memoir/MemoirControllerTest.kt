package snoopy.didit.memoir

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
import snoopy.didit.api.memoir.MemoirController
import snoopy.didit.domain.memoritag.MemoirTagFacade
import snoopy.didit.fixture.MemoirControllerTestFixture
import snoopy.util.ControllerTestSupport

private const val CREATE_MEMOIR_API = "/api/v1/memoirs"
private const val GET_MEMOIR_API = "/api/v1/memoirs/{memoir_id}"
private const val MODIFY_MEMOIR_API = "/api/v1/memoirs/modify"
private const val MEMOIR_ID = 1

@WebMvcTest(controllers = [MemoirController::class])
class MemoirControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var memoirTagFacade: MemoirTagFacade

    @Test
    @WithMockUser
    fun `회고록을 생성한다`() {
        val request = MemoirControllerTestFixture.getCreateMemoirRequest()
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.createMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_MEMOIR_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(MEMOIR_ID))
            .andExpect(jsonPath("$.data.title").value("회고록 제목"))
            .andExpect(jsonPath("$.data.writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data.content").value("회고록 내용"))
            .andExpect(jsonPath("$.data.tags[0].name").value("태그1"))
            .andExpect(jsonPath("$.data.tags[1].name").value("태그2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `회고록을_생성시_제목이_null이면 V001에러를 뱉는다`() {
        val request = MemoirControllerTestFixture.getNullTitleCreateMemoirRequest()
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.createMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_MEMOIR_API)
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
            .andExpect(jsonPath("$.error.data").value("제목은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `회고록을_생성시_제목이_1~50자가 아니면 V001에러를 뱉는다`() {
        val request = MemoirControllerTestFixture.getTitleIsMoreThan50CreateMemoirRequest()
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.createMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_MEMOIR_API)
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
            .andExpect(jsonPath("$.error.data").value("제목은 1~50자 사이여야 합니다"))
    }

    @Test
    @WithMockUser
    fun `회고록을_생성시_템플릿은_필수이다`() {
        val request = MemoirControllerTestFixture.getCreateMemoirRequestWithOutTemplate()
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.createMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_MEMOIR_API)
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
            .andExpect(jsonPath("$.error.data").value("템플릿은 필수 입력값입니다"))
    }

    @Test
    @WithMockUser
    fun `회고록을_생성시_태그제목_필수이다`() {
        val request = MemoirControllerTestFixture.getCreateMemoirRequestWithNullTitleTags()
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.createMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            post(CREATE_MEMOIR_API)
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
            .andExpect(jsonPath("$.error.data").value("태그 이름은 필수값 입니다"))
    }

    @Test
    @WithMockUser
    fun `회고록을 조회한다`() {
        val response = MemoirControllerTestFixture.getMemoirTagInfoDto()

        every { memoirTagFacade.findMemoirTagBy(any()) }.returns(response)

        mockMvc.perform(
            get(GET_MEMOIR_API, MEMOIR_ID),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(MEMOIR_ID))
            .andExpect(jsonPath("$.data.title").value("회고록 제목"))
            .andExpect(jsonPath("$.data.writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data.content").value("회고록 내용"))
            .andExpect(jsonPath("$.data.tags[0].name").value("태그1"))
            .andExpect(jsonPath("$.data.tags[1].name").value("태그2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `회고록을 수정한다`() {
        val request = MemoirControllerTestFixture.getModifyMemoirRequest()
        val response = MemoirControllerTestFixture.getModifiedMemoirTagInfoDto()

        every { memoirTagFacade.modifyMemoirTag(any()) }.returns(response)

        mockMvc.perform(
            patch(MODIFY_MEMOIR_API)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(MEMOIR_ID))
            .andExpect(jsonPath("$.data.title").value("업데이트 회고록 제목"))
            .andExpect(jsonPath("$.data.writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data.content").value("업데이트 회고록 내용"))
            .andExpect(jsonPath("$.data.tags[0].name").value("업데이트 태그1"))
            .andExpect(jsonPath("$.data.tags[1].name").value("업데이트 태그2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `회고록을 삭제한다`() {
        val response = MemoirControllerTestFixture.getMemoirInfoDto()

        every { memoirTagFacade.deleteMemoirTagBy(any()) }.returns(response)

        mockMvc.perform(
            patch(GET_MEMOIR_API, MEMOIR_ID)
                .with(csrf()),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(MEMOIR_ID))
            .andExpect(jsonPath("$.data.title").value("회고록 제목2"))
            .andExpect(jsonPath("$.data.writeDate").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.data.content").value("회고록 내용2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }
}
