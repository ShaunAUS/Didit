package snoopy.didit.domain.resume

import org.springframework.stereotype.Service
import snoopy.didit.domain.resume.dto.CareerUpdateRequest

@Service
class CareerProcessor(
    private val careerFinder: CareerFinder,
    private val careerUpdater: CareerUpdater,
) {
    fun update(request: CareerUpdateRequest) {
        val career: Career = careerFinder.find(request.careerId)
        career.update(request)

        careerUpdater.update(career)
    }
}
