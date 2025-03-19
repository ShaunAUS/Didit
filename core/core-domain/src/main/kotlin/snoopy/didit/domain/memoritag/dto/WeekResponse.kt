package snoopy.didit.domain.memoritag.dto

data class WeekResponse(
    val year: Int,
    val month: Int,
    val weekNumber: Int,
    val startDay: Int,
    val endDay: Int,
)
