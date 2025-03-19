package snoopy.didit.domain.tag.dto

import snoopy.didit.domain.tag.Tag

data class TagResponse(
    val name: String,
)

fun Tag.toResponse(): TagResponse {
    return TagResponse(
        name = this.name,
    )
}
