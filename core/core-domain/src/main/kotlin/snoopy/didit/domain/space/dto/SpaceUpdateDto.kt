package snoopy.didit.domain.space.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "공간 수정 요청 DTO")
data class SpaceUpdateDto(
    @Schema(description = "공간 ID", example = "1", required = true)
    val spaceId: Long,
    @field:NotBlank(message = "제목은 필수 입력값입니다")
    @field:Size(min = 1, max = 50, message = "제목은 1~50자 사이여야 합니다")
    @Schema(description = "공간 이름", example = "회의실", required = true)
    val name: String?,
)
