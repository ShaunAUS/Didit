package snoopy.didit.domain.tag.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "태그 생성 요청 DTO")
data class TagCreateDto(
    @Schema(description = "태그 이름", example = "Work", required = true)
    @field:NotBlank(message = "태그 이름은 필수값 입니다")
    @field:Size(min = 1, max = 20, message = "제목은 1~20자 사이여야 합니다")
    val name: String?,
)
