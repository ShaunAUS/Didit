package snoopy.didit.resume

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.resume.Resume

interface ResumeJpaRepository : JpaRepository<Resume, Long>
