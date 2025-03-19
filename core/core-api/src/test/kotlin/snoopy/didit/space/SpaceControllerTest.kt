package snoopy.didit.space

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import snoopy.didit.api.space.SpaceController
import snoopy.didit.domain.space.SpaceService
import snoopy.didit.domain.space.dto.SpaceCreateDto
import snoopy.didit.domain.space.dto.SpaceResponse
import snoopy.didit.domain.space.dto.SpaceUpdateDto
import snoopy.util.ControllerTestSupport

@WebMvcTest(controllers = [SpaceController::class])
class SpaceControllerTest : ControllerTestSupport() {
    @MockkBean
    lateinit var spaceService: SpaceService

    @Test
    @WithMockUser
    fun `스페이스를 생성한다`() {
        val res = SpaceResponse(1, "space1")

        every { spaceService.createSpace(any()) }.returns(res)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/space")
                .content(objectMapper.writeValueAsString(SpaceCreateDto("space1")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("space1"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `스페이스_이름은_1~50자로_생성한다`() {
        val res = SpaceResponse(1, "space1")

        every { spaceService.createSpace(any()) }.returns(res)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/space")
                .content(
                    objectMapper.writeValueAsString(
                        SpaceCreateDto("space1ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"),
                    ),
                )
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
    fun `스페이스_이름은_Null이_될수_없다`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/space")
                .content(objectMapper.writeValueAsString(SpaceCreateDto(null)))
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
    fun `스페이스 이름을 수정 한다`() {
        val res = SpaceResponse(1, "updatedSpace1")
        val spaceUpdateDto = SpaceUpdateDto(1, "updatedSpace1")

        every { spaceService.updateSpace(any()) }.returns(res)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/space/modify")
                .content(objectMapper.writeValueAsString(spaceUpdateDto))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("updatedSpace1"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `해당멤버 Id로 스페이스를 조회 한다`() {
        val res =
            listOf(
                SpaceResponse(1, "space1"),
                SpaceResponse(1, "space2"),
            )

        every { spaceService.findSpaceByMemberId(any()) }.returns(res)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/space")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("space1"))
            .andExpect(jsonPath("$.data[1].id").value(1))
            .andExpect(jsonPath("$.data[1].name").value("space2"))
            .andExpect(jsonPath("$.error").isEmpty())
    }

    @Test
    @WithMockUser
    fun `스페이스를 삭제 한다`() {
        val res = SpaceResponse(1, "deletedSpace1")
        every { spaceService.deleteSpace(any()) }.returns(res)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/space/1/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("deletedSpace1"))
            .andExpect(jsonPath("$.error").isEmpty())
    }
}
