package snoopy.didit.domain.resume

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.member.MemberFinder
import snoopy.didit.domain.resume.dto.ResumeCreateRequest
import snoopy.didit.domain.resume.dto.ResumeResponse
import snoopy.didit.domain.resume.dto.toResponse

@Component
class ResumeAppender(
    private val memberFinder: MemberFinder,
    private val resumeRepository: ResumeRepository,
) {
    @Transactional
    fun init(request: ResumeCreateRequest): ResumeResponse {
        val member = memberFinder.find(memberId = request.memberId)

        val resume =
            Resume(
                email = member.email,
                memberId = request.memberId,
            )

        val savedResume =
            resumeRepository.save(
                resume = resume,
                memberId = request.memberId,
            )

        return savedResume.toResponse()
    }
}
