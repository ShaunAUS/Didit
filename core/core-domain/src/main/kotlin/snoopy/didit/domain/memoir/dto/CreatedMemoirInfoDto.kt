package snoopy.didit.domain.memoir.dto

import java.time.LocalDateTime

data class CreatedMemoirInfoDto(
    val id: Long,
    val writeDate: LocalDateTime,
    val title: String,
    val content: String,
)
