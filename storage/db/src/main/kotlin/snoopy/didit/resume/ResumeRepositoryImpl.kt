package snoopy.didit.resume

import org.springframework.stereotype.Repository
import snoopy.didit.domain.resume.Resume
import snoopy.didit.domain.resume.ResumeRepository
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Repository
class ResumeRepositoryImpl(
    private val resumeJpaRepository: ResumeJpaRepository,
) : ResumeRepository {
    override fun save(
        resume: Resume,
        memberId: Long,
    ): Resume {
        val savedEntity = resumeJpaRepository.save(resume)
        return savedEntity
    }

    override fun find(resumeId: Long): Resume =
        resumeJpaRepository.findById(resumeId).orElseThrow {
            BusinessException(ErrorCode.NOT_FOUND_RESUME)
        }

    override fun update(resume: Resume) {
        resumeJpaRepository.save(resume)
    }
}
