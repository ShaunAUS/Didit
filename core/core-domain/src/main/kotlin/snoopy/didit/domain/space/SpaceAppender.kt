package snoopy.didit.domain.space

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.space.dto.SpaceCreateDto

@Component
class SpaceAppender(
    private val spaceRepository: SpaceRepository,
) {
    @Transactional
    fun createSpace(spaceCreateDto: SpaceCreateDto): Space {
        return spaceRepository.createSpace(spaceCreateDto)
    }

    @Transactional
    fun save(space: Space): Space {
        return spaceRepository.save(space)
    }
}
