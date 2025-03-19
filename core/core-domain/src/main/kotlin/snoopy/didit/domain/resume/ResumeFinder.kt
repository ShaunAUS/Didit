package snoopy.didit.domain.resume

import org.springframework.stereotype.Component

@Component
class ResumeFinder(
    private val resumeRepository: ResumeRepository,
) {
    fun find(resumeId: Long): Resume = resumeRepository.find(resumeId)
}
