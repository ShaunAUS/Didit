package snoopy.didit.domain.memoir.dto

import snoopy.didit.domain.memoritag.dto.MemoirTagResponse
import snoopy.didit.domain.memoritag.dto.WeekResponse

data class MemoirTagWithDateResponse(
    val month: Int,
    val weekNumber: Int,
    val startDay: Int,
    val endDay: Int,
    val memoirTagResponses: List<MemoirTagResponse>?,
) {
    companion object {
        fun of(
            month: Int,
            weekResponse: WeekResponse,
            memoirTagResponses: List<MemoirTagResponse>,
        ): MemoirTagWithDateResponse {
            return MemoirTagWithDateResponse(
                month = month,
                weekNumber = weekResponse.weekNumber,
                startDay = weekResponse.startDay,
                endDay = weekResponse.endDay,
                memoirTagResponses = memoirTagResponses,
            )
        }
    }
}
