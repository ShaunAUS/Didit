package snoopy.didit.domain.memoritag.dto

import snoopy.didit.memoir.Calendar

data class MemoirContinousWritingCalculateRequestDto(
    val year: Int,
    val month: Int,
) {
    companion object {
        fun of(
            year: Int,
            calendar: Calendar,
        ): MemoirContinousWritingCalculateRequestDto {
            return MemoirContinousWritingCalculateRequestDto(year, Calendar.toInt(calendar))
        }
    }
}
