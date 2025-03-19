package snoopy.didit.domain.space

import getOrThrow
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import snoopy.didit.domain.space.dto.SpaceCreateDto
import snoopy.didit.domain.space.dto.SpaceResponse
import snoopy.didit.domain.space.dto.SpaceUpdateDto
import snoopy.didit.domain.space.dto.toResponse

private val logger = KotlinLogging.logger {}

@Service
class SpaceService(
    private val spaceFinder: SpaceFinder,
    private val spaceAppender: SpaceAppender,
) {
    fun createSpace(spaceCreateDto: SpaceCreateDto): SpaceResponse {
        val createdSpace = spaceAppender.createSpace(spaceCreateDto)
        return createdSpace.toResponse()
    }

    fun findSpaceByMemberId(memberId: Long): List<SpaceResponse> {
        return spaceFinder.findSpacesBy(memberId)
            .map { it.toResponse() }
    }

    fun updateSpace(spaceUpdateDto: SpaceUpdateDto): SpaceResponse {
        val space = findSpaceBy(spaceUpdateDto.spaceId)
        space.modifySpaceName(spaceUpdateDto.name.getOrThrow())
        return spaceAppender.save(space).toResponse()
    }

    fun deleteSpace(spaceId: Long): SpaceResponse {
        val space = findSpaceBy(spaceId)
        space.deleteSpace()
        return spaceAppender.save(space).toResponse()
    }

    private fun findSpaceBy(spaceId: Long) = spaceFinder.findSpaceBy(spaceId)
}
