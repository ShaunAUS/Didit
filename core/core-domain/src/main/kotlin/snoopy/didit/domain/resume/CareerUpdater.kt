package snoopy.didit.domain.resume

import org.springframework.stereotype.Component

@Component
class CareerUpdater(
    private val careerRepository: CareerRepository,
) {
    fun update(career: Career) {
        careerRepository.update(career)
    }
}
