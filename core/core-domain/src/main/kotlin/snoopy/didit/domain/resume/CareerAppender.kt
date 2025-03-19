package snoopy.didit.domain.resume

import org.springframework.stereotype.Component
import snoopy.didit.domain.resume.dto.CareerCreateDto
import snoopy.didit.domain.resume.dto.CareerResponse
import snoopy.didit.domain.resume.dto.toResponse

@Component
class CareerAppender(
    private val careerRepository: CareerRepository,
) {
    fun init(request: CareerCreateDto): CareerResponse {
        val career: Career = Career(memberId = request.memberId, resumeId = request.resumeId)
        careerRepository.save(career)
        return career.toResponse()
    }
}
