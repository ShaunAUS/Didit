package snoopy.didit.memberskill

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.memberskill.MemberSkill

interface MemberSkillJpaRepository : JpaRepository<MemberSkill, Long>
