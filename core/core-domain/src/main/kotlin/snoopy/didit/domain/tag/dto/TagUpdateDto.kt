package snoopy.didit.domain.tag.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TagUpdateDto(
    @field:NotBlank(message = "태그 이름은 필수값 입니다")
    @field:Size(min = 1, max = 20, message = "제목은 1~20자 사이여야 합니다")
    val name: String?,
)
