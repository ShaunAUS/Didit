package snoopy.didit.domain.space

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class SpaceFinder(
    private val spaceRepository: SpaceRepository,
) {
    fun findSpacesBy(memberId: Long): List<Space> {
        return spaceRepository.findByMemberId(memberId)
    }

    fun findSpaceBy(spaceId: Long): Space {
        return spaceRepository.findById(spaceId)
    }
}
