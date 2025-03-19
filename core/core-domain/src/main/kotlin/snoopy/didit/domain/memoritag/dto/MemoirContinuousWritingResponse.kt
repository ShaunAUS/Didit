package snoopy.didit.domain.memoritag.dto

import java.time.LocalDate

data class MemoirContinuousWritingResponse(
    val continuousWriteDay: Int,
    val writtenDates: List<LocalDate>,
    val totalMemoirCount: Int,
    val randomMessage: String,
)
