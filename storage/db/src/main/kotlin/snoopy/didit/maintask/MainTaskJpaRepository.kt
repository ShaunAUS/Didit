package snoopy.didit.maintask

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.resume.MainTask

interface MainTaskJpaRepository : JpaRepository<MainTask, Long>
