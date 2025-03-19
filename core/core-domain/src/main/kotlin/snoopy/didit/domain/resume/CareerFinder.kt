package snoopy.didit.domain.resume

import org.springframework.stereotype.Component

@Component
class CareerFinder(
    private val careerRepository: CareerRepository,
) {
    fun find(careerId: Long): Career = careerRepository.find(careerId)
}
