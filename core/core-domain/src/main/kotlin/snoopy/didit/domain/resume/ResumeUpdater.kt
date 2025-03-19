package snoopy.didit.domain.resume

import org.springframework.stereotype.Component

@Component
class ResumeUpdater(
    private val resumeRepository: ResumeRepository,
) {
    fun update(resume: Resume) {
        resumeRepository.update(resume)
    }
}
