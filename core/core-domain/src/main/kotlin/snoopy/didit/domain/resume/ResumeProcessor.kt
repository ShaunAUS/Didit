package snoopy.didit.domain.resume

import org.springframework.stereotype.Service
import snoopy.didit.domain.resume.dto.ResumeUpdateRequest

@Service
class ResumeProcessor(
    private val resumeFinder: ResumeFinder,
    private val resumeUpdater: ResumeUpdater,
) {
    fun update(request: ResumeUpdateRequest) {
        val resume: Resume = resumeFinder.find(resumeId = request.resumeId)
        resume.update(request)

        resumeUpdater.update(resume)
    }
}
