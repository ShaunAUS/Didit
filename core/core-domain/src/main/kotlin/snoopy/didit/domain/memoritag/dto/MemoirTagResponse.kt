package snoopy.didit.domain.memoritag.dto

import getOrThrow
import snoopy.didit.domain.memoir.Memoir
import snoopy.didit.domain.tag.dto.TagResponse
import java.time.LocalDateTime

data class MemoirTagResponse(
    val id: Long,
    val writeDate: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<TagResponse>,
)

fun Memoir.toMemoirTagResponse(
    memoir: Memoir,
    createTags: List<TagResponse>?,
): MemoirTagResponse {
    return MemoirTagResponse(
        id = memoir.id.getOrThrow(),
        writeDate = memoir.writeDate,
        title = memoir.title,
        content = memoir.content,
        tags = createTags ?: emptyList(),
    )
}
