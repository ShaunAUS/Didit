package snoopy.didit.career

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.resume.Career

interface CareerJpaRepository : JpaRepository<Career, Long>
