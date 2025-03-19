package snoopy.didit.domain.memoir.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import snoopy.didit.domain.tag.dto.TagUpdateDto
import snoopy.didit.memoir.Template

@Schema(description = "회고록 수정 요청 DTO")
data class ModifyMemoirDto(
    @Schema(description = "회고록 ID", example = "4", required = true)
    @field:NotNull(message = "회고록 ID는 필수 입력값입니다")
    val id: Long?,
    @Schema(description = "회고록 제목", example = "회고록입니다88", required = true)
    @field:NotBlank(message = "회고록 제목은 필수 입력값입니다")
    @field:Size(min = 1, max = 50, message = "제목은 1~50자 사이여야 합니다")
    val title: String?,
    @Schema(description = "회고록 내용", example = "회고록 내용내용88", required = true)
    val content: String?,
    @Schema(description = "템플릿", example = "KPT", required = true)
    @field:NotNull(message = "템플릿은 필수 입력값입니다")
    val template: Template?,
    @Schema(
        description = "태그 목록",
        example = "[{\"name\": \"tag14\"}]",
        required = false,
    )
    @field:Valid
    val tags: List<TagUpdateDto>?,
)
