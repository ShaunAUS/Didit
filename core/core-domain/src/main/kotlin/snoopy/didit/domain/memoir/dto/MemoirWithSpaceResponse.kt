package snoopy.didit.domain.memoir.dto

import snoopy.didit.domain.memoritag.dto.MemoirTagResponse

data class MemoirWithSpaceResponse(
    val spaceName: String,
    val memoirTagResponses: MutableList<MemoirTagResponse>?,
)
