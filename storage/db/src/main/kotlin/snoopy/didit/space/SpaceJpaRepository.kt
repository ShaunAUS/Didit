package snoopy.didit.space

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.space.Space

interface SpaceJpaRepository : JpaRepository<Space, Long>
