package snoopy.didit.domain.memoir.dto

import getOrThrow
import snoopy.didit.domain.memoir.Memoir
import java.time.LocalDateTime

data class MemoirResponse(
    val id: Long,
    val writeDate: LocalDateTime,
    val title: String,
    val content: String,
)

fun Memoir.toMemoirResponse(memoir: Memoir): MemoirResponse {
    return MemoirResponse(
        id = memoir.id.getOrThrow(),
        writeDate = memoir.writeDate,
        title = memoir.title,
        content = memoir.content,
    )
}
