package snoopy.didit.domain.resume

interface ResumeRepository {
    fun save(
        resume: Resume,
        memberId: Long,
    ): Resume

    fun find(resumeId: Long): Resume

    fun update(resume: Resume)
}
