package snoopy.didit.memoir

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.memoir.Memoir

interface MemoirJpaRepository : JpaRepository<Memoir, Long>
