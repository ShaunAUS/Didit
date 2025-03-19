package snoopy.didit.tag

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.tag.Tag

interface TagJpaRepository : JpaRepository<Tag, Long>
