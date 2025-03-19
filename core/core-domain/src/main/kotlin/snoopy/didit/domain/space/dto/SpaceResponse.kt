package snoopy.didit.domain.space.dto

import getOrThrow
import snoopy.didit.domain.space.Space

data class SpaceResponse(
    val id: Long,
    val name: String,
)

fun Space.toResponse() =
    SpaceResponse(
        id = id.getOrThrow(),
        name = name,
    )
